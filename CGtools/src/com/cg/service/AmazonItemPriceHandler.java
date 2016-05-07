package com.cg.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.domain.AmazonItemPrice;
import com.cg.domain.JDSku;
import com.cg.service.remoteservice.AmazonRemoteInfoServiceAbstract;
import com.cg.service.remoteservice.RemoteInfoServiceHelper;
import com.cg.util.DBUtil;

public class AmazonItemPriceHandler {
	
	private AmazonItemPriceHandler(){}
	
	public static AmazonItemPriceHandler amazonItemPriceHandler = new AmazonItemPriceHandler();
	
	public static AmazonItemPriceHandler getInstance(){
		return amazonItemPriceHandler;
	}
	
	public void handleAmazonPrice(JDSku sku){
		removeSkuIdInExtendPrice(sku.getSku_id());
		if(!"10".equals(sku.getTypeId())){
			return;
		}
		List<AmazonRemoteInfoServiceAbstract> ifs = RemoteInfoServiceHelper.getAmazonItemRemoteInfoServices();
		List<AmazonItemPrice> list = new ArrayList<AmazonItemPrice>();
		for(AmazonRemoteInfoServiceAbstract remoteInfoService : ifs){
			try{
				CloseableHttpClient httpclient = HttpClients.createDefault();
				String price = remoteInfoService.getPrice(httpclient, sku.getSku_id());
				if(StringUtils.isBlank(price)){
					continue;
				}
				double remotePrice = 0d;
				try{
					remotePrice = Double.parseDouble(price);
				}catch (Exception e) {
					continue;
				}
				AmazonItemPrice aip = new AmazonItemPrice(sku.getSku_id(), remoteInfoService.getCountryCode(), remotePrice);
				list.add(aip);
			}catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		saveAmazonItem(list);
	}
	
	public void saveAmazonItem(List<AmazonItemPrice> list){
		if(list == null || list.isEmpty()){
			return;
		}
		for(AmazonItemPrice aip : list){
			addSkuPirce(aip);
		}
	}
	
	private void removeSkuIdInExtendPrice(String skuId){
		String sql = "delete from sku_price_ext where sku_id=?";
		DBUtil.executeUpdate(sql, new Object[]{skuId});
	}
	
	private void addSkuPirce(AmazonItemPrice aip){
		String sql = "insert into sku_price_ext (sku_id,country,price) values (?,?,?)";
		DBUtil.executeUpdate(sql, new Object[]{aip.getSkuId(),aip.getCountryCode(),aip.getPrice()});
	}
}
