package com.cg.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cg.domain.Item;
import com.cg.domain.JDSku;
import com.cg.domain.Sku;
import com.cg.exception.CGexception;
import com.cg.util.ItemComparator;

public class PriceCaculatorService {

	private float standPrice = 200;

	private float maxPrice = 210;
	
	private float huafeiPrice = 1;

	private Map<Float, List<Sku>> tempData = new HashMap<Float, List<Sku>>();

	//计算后最终库存数
	private Map<String, Integer> inventoryData = new HashMap<String, Integer>();
	
	private Map<String, Integer> originalInventoryData;

	private List<Sku> badSku = new ArrayList<Sku>();
	
	private List<Item> buyList = new ArrayList<Item>();

	List<Sku> removedSku = new ArrayList<Sku>();
	
	public String type;
	
	public static String INVENTORY_DATA = "inventoryData";
	
	public static String ORIGINAL_INVENTORY_DATA = "originalInventoryData";
	
	public static String ITEMS = "items";
	
	public static String BUY_LIST = "buyList";
	
	private PriceCaculatorService(){}
	
	public PriceCaculatorService(float standPrice, float maxPrice, float huafeiPrice, String type){
		this.standPrice = standPrice;
		this.maxPrice = maxPrice;
		this.huafeiPrice = huafeiPrice;
		this.type = type;
	}
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PriceCaculatorService t = new PriceCaculatorService();
//		List<Sku> items = t.getItemFromExcel();
//		Collections.sort(items);
		// print(items);
//		t.handleItems(items);
	}
	
	public Map<String, Object> getResult(InputStream is) throws IOException{
		List<Sku> items = getItemFromExcel(is);
		Collections.sort(items);
		Map map = new HashMap();
		handleItems(items);
		map.put(ORIGINAL_INVENTORY_DATA, originalInventoryData);
		map.put(INVENTORY_DATA, inventoryData);
		map.put(ITEMS, items);
		map.put(BUY_LIST, buyList);
		Collections.sort(buyList, new ItemComparator());
		return map;
	}
	
	public Map<String, Object> getResultFromDB(String[] itemTypes, String special_type, String[] exclude_itemTypes, String exclude_special_type) throws IOException{
		List<JDSku> jdSkus = JDSkuService.getInstance().queryAllJDWithPriceSkus(itemTypes,special_type, exclude_itemTypes, exclude_special_type);
		return getResultFromJDSkus(jdSkus);
	}
	
	public Map<String, Object> getResultFromDB1(String idsStr) throws IOException{
		if(StringUtils.isBlank(idsStr)){
			return null;
		}
		String[] ids = idsStr.split(",");
		StringBuilder sb = new StringBuilder("select * from sku where sku_id in (");
		int i = 0;
		for(String id : ids){
			if(i!=0){
				sb.append(",");
			}
			sb.append("'").append(id).append("'");
			i++;
		}
		sb.append(")");
		List<JDSku> jdSkus = JDSkuService.getInstance().queryJDSkuBySQL(sb.toString(), Collections.emptyList());
		return getResultFromJDSkus(jdSkus);
	}
	
	public Map<String, Object> getResultFromJDSkus(List<JDSku> jdSkus) throws IOException{
		List<Sku> items = transferSku(jdSkus);
		Collections.sort(items);
		Map map = new HashMap();
		handleItems(items);
		map.put(ORIGINAL_INVENTORY_DATA, originalInventoryData);
		map.put(INVENTORY_DATA, inventoryData);
		map.put(ITEMS, items);
		map.put(BUY_LIST, buyList);
		Collections.sort(buyList, new ItemComparator());
		return map;
	}
	
	private List<Sku> transferSku(List<JDSku> jdSkus){
		List<Sku> newSku = new ArrayList<Sku>();
		for(JDSku jdsku : jdSkus){
			Sku sku = new Sku();
			sku.setJdid(jdsku.getSku_id());
			sku.setPrice(jdsku.getPrice());
			sku.setSale(jdsku.getImportant());
			sku.setTitle(jdsku.getName());
			sku.setHasstock(jdsku.getInstock());
			sku.setCgid(jdsku.getCgId());
			sku.setPublishPrice(jdsku.getPublishingPrice());
			newSku.add(sku);
			inventoryData.put(sku.getJdid(), jdsku.getBuynumber());
		}
		originalInventoryData = copyInventoryData(inventoryData);
		return newSku;
	}

	public void handleItems(List<Sku> skus) {
		for (int i = 0; i < skus.size(); i++) {
			Sku sku = skus.get(i);
			if (!hasStock(sku, inventoryData)) {
				continue;
			}
			tempData.clear();
			Map<String, Integer> tempInventory = copyInventoryData(inventoryData);
			List<Sku> oldSkus = new ArrayList<Sku>();
			subSkuFromList(sku, tempInventory);
			handleItem(oldSkus, sku, i, skus, tempInventory);
			if (!tempData.isEmpty()) {
//				Map.Entry<Float, List<Sku>> entry = getMinPriceItems(tempData);
				Item item = getMoreImportantSkuCountAndMinLogicPriceItem(tempData);
//				handleInventory(entry, skus);
				refreshItemInventory(item);
			}
		}
	}

	private void handleItem(List<Sku> oldItems, Sku item, int index,
			List<Sku> restSkus, Map<String, Integer> tempInventory) {
		float sumPrice = 0;
		for (Sku oldItem : oldItems) {
			sumPrice += oldItem.getPrice();
		}
		sumPrice += item.getPrice();
		float tempPrice = sumPrice;
		for (int i = index; i < restSkus.size(); i++) {
			Sku sku = restSkus.get(i);
			if(!hasStock(sku, tempInventory)){
				continue;
			}
			if("1".equals(type) && item.getTitle().equals(sku.getTitle())){
				continue;
			}
			sumPrice = tempPrice;
			sumPrice = sumPrice + sku.getPrice();
			if (sumPrice >= standPrice && sumPrice <= maxPrice) {
				List<Sku> newList = copyList(oldItems);
				newList.add(item);
				newList.add(sku);
				if(tempData.containsKey(sumPrice)){
					List<Sku> oldSkuList = tempData.get(sumPrice);
					int oldImportantSkuNumber = getImportantSkuNumber(oldSkuList);
					int newImportantSkuNumber = getImportantSkuNumber(newList);
					if(newImportantSkuNumber>oldImportantSkuNumber){
						tempData.put(sumPrice, newList);
					}
				}else{
					tempData.put(sumPrice, newList);
				}
			} else if (sumPrice < standPrice) {
				List<Sku> newOldList = copyList(oldItems);
				newOldList.add(item);
				subSkuFromList(sku, tempInventory);
				handleItem(newOldList, sku, i, restSkus, tempInventory);
			}
		}
	}

	private void refreshItemInventory(Item item) {
		buyList.add(item);
		Set<Sku> skuSet = item.getSkus().keySet();
		for (Sku sku : skuSet) {
			inventoryData.put(sku.getJdid(),
					inventoryData.get(sku.getJdid()) - item.getSkus().get(sku));
		}
	}
	
	private String printSkusInfo(Map<Sku,Integer> skusInfo){
		String s = "";
		Set<Map.Entry<Sku, Integer>> entrySet = skusInfo.entrySet();
		for(Map.Entry<Sku, Integer> entry : entrySet){
			s += ", " + entry.getKey().getTitle() + " : " + entry.getValue();
		}
		return s;
	}
	
	private Item getMinLogicPriceItem(
			Map<Float, List<Sku>> tempData) {
		List<Item> itemList = new ArrayList<Item>();
		Iterator<Map.Entry<Float, List<Sku>>> itr = tempData.entrySet()
				.iterator();
		while (itr.hasNext()) {
			Map.Entry<Float, List<Sku>> entry1 = itr.next();
			Item item = new Item();
			item.setTotalPrice(entry1.getKey());
			List<Sku> list = entry1.getValue();
			Map<Sku,Integer> skusInfo = new HashMap<Sku, Integer>();
			for(Sku sku : list){
				if(skusInfo.containsKey(sku)){
					skusInfo.put(sku, skusInfo.get(sku)+1);
				}else{
					skusInfo.put(sku, 1);
				}
			}
			item.setSkus(skusInfo);
			item.setLogicPrice(generateLogicPrice(skusInfo,entry1.getKey()));
			itemList.add(item);
		}
		Collections.sort(itemList);
		return itemList.isEmpty()?null:itemList.get(0);
	}
	
	private Item getMoreImportantSkuCountAndMinLogicPriceItem(
			Map<Float, List<Sku>> tempData) {
		//先找出重要sku个数最多的 list,再找出价格最便宜的一组
		int maxImportantNumber = 0;//
		Map<Float, List<Sku>> tempData1 = new HashMap<Float, List<Sku>>();
		Set<Map.Entry<Float, List<Sku>>> set = tempData.entrySet();
		for(Map.Entry<Float, List<Sku>> entry : set){
			int importantNum = getImportantSkuNumber(entry.getValue());
			if(importantNum == maxImportantNumber){
				tempData1.put(entry.getKey(), entry.getValue());
			}else if(importantNum > maxImportantNumber){
				tempData1.clear();
				maxImportantNumber = importantNum;
				tempData1.put(entry.getKey(), entry.getValue());
			}
		}
		return getMinLogicPriceItem(tempData1);
	}
	
	/*
	 * 获取sku中重要的个数
	 */
	private int getImportantSkuNumber(List<Sku> tempData){
		int num = 0;
		for(Sku sku : tempData){
			if(sku.isSale()){
				num++;
			}
		}
		return num;
	}
	
	private float generateLogicPrice(Map<Sku,Integer> skusInfo, float price){
		Collection<Integer> values = skusInfo.values();
		int duplicateNumber = 0;
		for(Integer number : values){
			if(number>1){
				duplicateNumber = duplicateNumber + number - 1;
			}
		}
		return price + duplicateNumber * huafeiPrice;
	}

	private Map.Entry<Float, List<Sku>> getMinPriceItems(
			Map<Float, List<Sku>> tempData) {
		Map.Entry<Float, List<Sku>> entry = null;
		float minPrice = Float.MAX_VALUE;
		Iterator<Map.Entry<Float, List<Sku>>> itr = tempData.entrySet()
				.iterator();
		while (itr.hasNext()) {
			Map.Entry<Float, List<Sku>> entry1 = itr.next();
			if (entry1.getKey() < minPrice) {
				minPrice = entry1.getKey();
				entry = entry1;
			}
		}
		return entry;
	}

	private List<Sku> copyList(List<Sku> list) {
		List<Sku> l = new ArrayList<Sku>();
		for (Sku sku : list) {
			l.add(sku);
		}
		return l;
	}

	private void subSkuFromList(Sku includedSku,
			Map<String, Integer> tempInventoryData) {
		tempInventoryData.put(includedSku.getJdid(), tempInventoryData
				.get(includedSku.getJdid()) - 1);
	}

	private boolean hasStock(Sku sku, Map<String, Integer> tempInventoryData) {
		if (tempInventoryData.get(sku.getJdid()) <= 0) {
			return false;
		}
		//如果京东无库存,不参与计算
		if(!sku.isHasstock()){
			return false;
		}
		return true;
	}

	private void removeBadItems(List<Sku> skus) {
		List<Sku> removedSkus = new ArrayList<Sku>();
		for (Sku sku : skus) {
			if (sku.getPrice() > maxPrice) {
				badSku.add(sku);
				removedSkus.add(sku);
			}
		}
		skus.removeAll(removedSkus);

	}

	private Map<String, Integer> copyInventoryData(
			Map<String, Integer> inventoryData) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Set<Map.Entry<String, Integer>> entrySet = inventoryData.entrySet();
		for (Map.Entry<String, Integer> entry : entrySet) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}
	
	public List<Sku> getResult1(InputStream is) throws IOException{
		List<Sku> items = getItemFromExcel(is);
//		Collections.sort(items);
//		print(items);
//		t.handleItems(items);
		return items;
	}
	
	private List<Sku> getItemFromExcel(InputStream is) throws IOException {

		XSSFWorkbook xwb = new XSSFWorkbook(is);
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row;
		String cell;
		List<Sku> items = new ArrayList<Sku>();
		for (int i = sheet.getFirstRowNum() + 1; i < sheet
				.getPhysicalNumberOfRows(); i++) {
			try{
				row = sheet.getRow(i);
				Sku item = new Sku();
				String title = row.getCell(0).toString().trim();
				item.setTitle(title);
				String jdid = row.getCell(1) == null?null:row.getCell(1).toString().trim();
				item.setJdid(jdid);
				String stockStr = row.getCell(2).toString().trim();
				float stockFlo = Float.parseFloat(stockStr);
				int stock = (int) stockFlo;
				if (inventoryData.containsKey(jdid)) {
					throw new CGexception("Title : \"" + jdid + "\" 有重复");
				} else {
					inventoryData.put(jdid, stock);
				}
				float price = Float.parseFloat(row.getCell(3).toString().trim());
				item.setPrice(price);
				String sale = row.getCell(4) == null ? null : row.getCell(4)
						.toString();
				item.setSale(!isBlank(sale));
				items.add(item);
			} catch (CGexception e) {
				throw e;
			}
			catch (Exception e) {
				throw new CGexception("第" + i + "行附近内容有错误 请检查",e);
			}
		}
		originalInventoryData = copyInventoryData(inventoryData);
		return items;
	}
	private static boolean isBlank(String str) {
		if (str == null || str.trim().length() < 1) {
			return true;
		}
		return false;
	}
	
	
	public void transferHihtPriorityItem(List<Item> buyList, Map<String, Integer> inventoryData, List<Sku> items){
		boolean hasHighPriorityItemLeft = false;
		// 循环所有剩余item 检查sale=1的item是否还未采购
		for(Sku sku : items){
			if(sku.isSale()){
				int inventory = inventoryData.get(sku.getJdid());
				if(inventory > 0){
					hasHighPriorityItemLeft = true;
					break;
				}
			}
		}
		if(!hasHighPriorityItemLeft){
			return;
		}
		// 算法: 找出sale=1的item的价格 然后去替换buyList中不是sale=1的item.
		
	}
}
