package com.cg.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.cg.domain.JDSku;
import com.cg.domain.UpdatedSku;
import com.cg.exception.CGexception;
import com.cg.util.DBUtil;
import com.cg.service.JDSkuService;

/**
 * Servlet implementation class JDSkuManageAction
 */
public class JDSkuManageAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JDSkuManageAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		action(request, response);
	}

	private void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");
		if("add".equalsIgnoreCase(action)){
			add(request, response);
		}else if("remove".equalsIgnoreCase(action)){
			remove(request, response);
		}else if("update".equalsIgnoreCase(action)){
			update(request, response);
		}else if("query".equalsIgnoreCase(action)){
			query(request, response);
		}else if("find".equalsIgnoreCase(action)){
			find(request, response);
		}else if("removeStock".equalsIgnoreCase(action)){
			removeStock(request, response);
		}else if("removeStockByText".equalsIgnoreCase(action)){
			removeStockByText(request, response);
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String cgId = request.getParameter("cgId");
		String skuId = request.getParameter("skuId");
		String name = DBUtil.transferChar(request.getParameter("name"));
		String buynumberStr = request.getParameter("buynumber");
		String priceStr = request.getParameter("price");
		String important = request.getParameter("important");
		String instock = request.getParameter("instock");
		String publishingPriceStr = request.getParameter("publishingPrice");
		String type = request.getParameter("type");
		String url = request.getParameter("url");
		String barcode = request.getParameter("barcode");
		String special_type = request.getParameter("special_type");
		if (StringUtils.isBlank(cgId) || StringUtils.isBlank(skuId) || StringUtils.isBlank(name)
				|| StringUtils.isBlank(buynumberStr)
				|| StringUtils.isBlank(important)) {
			throw new CGexception("请输入完整");
		}
		JDSku sku = new JDSku();
		sku.setCgId(cgId);
		sku.setSku_id(skuId);
		sku.setName(name);
		sku.setBuynumber(Integer.parseInt(buynumberStr));
		if(!StringUtils.isBlank(priceStr)){
			sku.setPrice(Float.parseFloat(priceStr));
		}
		if(!StringUtils.isBlank(publishingPriceStr)){
			sku.setPublishingPrice(Float.parseFloat(publishingPriceStr));
		}
		sku.setImportant("1".equals(important)?true:false);
		if(!StringUtils.isBlank(instock)){
			sku.setInstock("1".equals(instock)?true:false);
		}
		sku.setTypeId(type);
		sku.setSpecial_type(special_type);
		sku.setBarcode(barcode);
		boolean result = JDSkuService.getInstance().addJDSku(sku);
		if(result){
			request.setAttribute("result", "success");
		}
		JDSkuService.getInstance().refreshImportantProperty();
		if(StringUtils.isBlank(url)){
			request.getRequestDispatcher("/addSku.jsp").forward(request, response);
		}else{
			response.sendRedirect(url);
		}
		
	}
	
	private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String skuId = request.getParameter("skuId");
		JDSkuService.getInstance().removeJDSku(skuId);
		request.getRequestDispatcher("/skuList.jsp").forward(request, response);
	}
	private void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String skuId = request.getParameter("skuId");
		JDSku sku = JDSkuService.getInstance().findJDSku(skuId);
		request.setAttribute("sku", sku);
		request.getRequestDispatcher("/updateSku.jsp").forward(request, response);
	}
	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String cgId = request.getParameter("cgId");
		String skuId = request.getParameter("skuId");
		String newSkuId = request.getParameter("new_skuId");
		String name = DBUtil.transferChar(request.getParameter("name"));
		String buynumberStr = request.getParameter("buynumber");
		String priceStr = request.getParameter("price");
		String important = request.getParameter("important");
		String instock = request.getParameter("instock");
		String publishingPriceStr = request.getParameter("publishingPrice");
		String special_type = request.getParameter("special_type");
		String type = request.getParameter("type");
		String barcode = request.getParameter("barcode");
		if (StringUtils.isBlank(cgId)|| StringUtils.isBlank(skuId) || StringUtils.isBlank(name)
				|| StringUtils.isBlank(buynumberStr)
				|| StringUtils.isBlank(priceStr)
				|| StringUtils.isBlank(important) || StringUtils.isBlank(instock)) {
			throw new CGexception("请输入完整");
		}
		UpdatedSku sku = new UpdatedSku();
		sku.setCgId(cgId);
		sku.setSku_id(skuId);
		sku.setName(name);
		sku.setBuynumber(Integer.parseInt(buynumberStr));
		sku.setPrice(Float.parseFloat(priceStr));
		sku.setTypeId(type);
		sku.setSpecial_type(special_type);
		sku.setNew_sku_id(newSkuId);
		sku.setBarcode(barcode);
		if(!StringUtils.isBlank(publishingPriceStr)){
			sku.setPublishingPrice(Float.parseFloat(publishingPriceStr));
		}
		sku.setImportant("1".equals(important)?true:false);
		sku.setInstock("1".equals(instock)?true:false);
		boolean result = JDSkuService.getInstance().updateJDSkus(sku);
		JDSkuService.getInstance().refreshImportantProperty();
		request.getRequestDispatcher("/skuList.jsp").forward(request, response);
	}
	
	private void saveParameterToSession(HttpServletRequest request){
		request.getSession().setAttribute("query_skuId", request.getParameter("skuId"));
		request.getSession().setAttribute("query_cgId", request.getParameter("cgId"));
		request.getSession().setAttribute("query_name", DBUtil.transferChar(request.getParameter("name")));
		request.getSession().setAttribute("query_important", request.getParameter("important"));
		request.getSession().setAttribute("query_instock", request.getParameter("instock"));
		request.getSession().setAttribute("query_type", request.getParameter("type"));
		request.getSession().setAttribute("special_type", request.getParameter("special_type"));
		request.getSession().setAttribute("query_currentPage", request.getParameter("currentPage"));
		request.getSession().setAttribute("query_sortName", request.getParameter("sortName"));
		request.getSession().setAttribute("query_sort", request.getParameter("sort"));
	}
	
	private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String currentPageStr = request.getParameter("currentPage");
		String sortName = request.getParameter("sortName");
		String sort = request.getParameter("sort");
		
		String skuIdStr = null;
		String cgIdStr = null;
		String nameStr = null;
		String importantStr = null;
		String instock = null;
		String type = null;
		String special_type = null;
		String barcode = null;
		//click search button
		if(StringUtils.isBlank(currentPageStr) && StringUtils.isBlank(sortName) && StringUtils.isBlank(sort)){
			saveParameterToSession(request);
			skuIdStr = request.getParameter("skuId");
			cgIdStr = request.getParameter("cgId");
			nameStr = DBUtil.transferChar(request.getParameter("name"));
			importantStr = request.getParameter("important");
			instock = request.getParameter("instock");
			type = request.getParameter("type");
			special_type = request.getParameter("special_type");
			barcode = request.getParameter("barcode");
		}else{
			// 分页或者排序
			skuIdStr = (String)request.getSession().getAttribute("query_skuId");
			cgIdStr = (String)request.getSession().getAttribute("query_cgId");
			nameStr = (String)request.getSession().getAttribute("query_name");
			importantStr = (String)request.getSession().getAttribute("query_important");
			instock = (String)request.getSession().getAttribute("query_instock");
			type = (String)request.getSession().getAttribute("query_type");
			special_type = (String)request.getSession().getAttribute("special_type");
			barcode = (String)request.getSession().getAttribute("query_barcode");
			
		}
		String skuId = skuIdStr==null?null:skuIdStr.trim();
		String cgId = cgIdStr==null?null:cgIdStr.trim();
		String name = nameStr==null?null:nameStr.trim();
		Boolean important = StringUtils.isBlank(importantStr)?null:"1".equals(importantStr)?true:false;
		Boolean jd_instock = StringUtils.isBlank(instock)?null:"1".equals(instock)?true:false;
		int currentPage = StringUtils.isBlank(currentPageStr)?1:Integer.parseInt(currentPageStr);
		Map<String,Object> result = JDSkuService.getInstance().queryALL(skuId,cgId,name,important,jd_instock,type,special_type,barcode,currentPage,sortName,sort);
		request.setAttribute("resultMap", result);
		request.setAttribute("sort", sort);
		request.setAttribute("sortName", sortName);
		request.getRequestDispatcher("/skuList.jsp").forward(request, response);
//		if (StringUtils.isBlank(skuId) && StringUtils.isBlank(name)
//				&& StringUtils.isBlank(importantStr) && StringUtils.isBlank(instock)) {
//			//query all
//
//			
//			Map<String,Object> result = JDSkuService.getInstance().queryALL(currentPage,sortName,sort);
//			request.setAttribute("resultMap", result);
//			request.setAttribute("sort", sort);
//			request.setAttribute("sortName", sortName);
//			request.getRequestDispatcher("/skuList.jsp").forward(request, response);
//		}else{
//			Boolean important = StringUtils.isBlank(importantStr)?null:"1".equals(importantStr)?true:false;
//			Boolean jd_instock = StringUtils.isBlank(instock)?null:"1".equals(instock)?true:false;
//			List<JDSku> skus = JDSkuService.getInstance().queryJDSkus(skuId, name, important, jd_instock);
//			request.setAttribute("querySkus", skus);
//			request.getRequestDispatcher("/skuList.jsp").forward(request, response);
//		}
	}
	
	private void removeStock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String[] orders = request.getParameterValues("order");
		//20062246:1,20061230:1,20050028:1
		Map<String,Integer> maps = new HashMap<String, Integer>();
		for(String orderStr : orders){
			String[] skuAndBuyNumbers = orderStr.split(",");
			for(String skuAndBuyNumber : skuAndBuyNumbers){
				String sku = skuAndBuyNumber.split(":")[0];
				String buyNumber = skuAndBuyNumber.split(":")[1];
				if(maps.containsKey(sku)){
					maps.put(sku, maps.get(sku)+Integer.parseInt(buyNumber));
				}else{
					maps.put(sku, Integer.parseInt(buyNumber));
				}
			}
		}
		Set<Map.Entry<String, Integer>> set = maps.entrySet();
		List<JDSku> skus = new ArrayList<JDSku>();
		for(Map.Entry<String, Integer> entry : set){
			JDSku jdSku = new JDSku();
			jdSku.setSku_id(entry.getKey());
			jdSku.setBuynumber(entry.getValue());
			skus.add(jdSku);
		}
		JDSkuService.getInstance().removeStock(skus);
		JDSkuService.getInstance().refreshImportantProperty();
	}
	
	private void removeStockByText(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//{1-[20053414:1,20068536:1,20066630:1]}|{2-[20053414:1,20068536:1,20066630:1]}
		String inventoryInfos = request.getParameter("inventoryInfo");
		if(StringUtils.isBlank(inventoryInfos)){
			response.sendRedirect("qukucun.jsp");
			return;
		}
		Map<String,Integer> maps = new HashMap<String, Integer>();
		String[] itemInventoryInfos = inventoryInfos.split("\\|");
		//itemInventoryInfo = "{1-[20053414:1,20068536:1,20066630:1]}"
		for(String itemInventoryInfo : itemInventoryInfos){
			if(StringUtils.isBlank(itemInventoryInfo)){
				continue;
			}
			//orderStr = "20053414:1,20068536:1,20066630:1";
			String orderStr = StringUtils.substringBetween(itemInventoryInfo,"[", "]");
			String[] skuAndBuyNumbers = orderStr.split(",");
			for(String skuAndBuyNumber : skuAndBuyNumbers){
				String sku = skuAndBuyNumber.split(":")[0];
				String buyNumber = skuAndBuyNumber.split(":")[1];
				if(maps.containsKey(sku)){
					maps.put(sku, maps.get(sku)+Integer.parseInt(buyNumber));
				}else{
					maps.put(sku, Integer.parseInt(buyNumber));
				}
			}
			
		}
		Set<Map.Entry<String, Integer>> set = maps.entrySet();
		List<JDSku> skus = new ArrayList<JDSku>();
		for(Map.Entry<String, Integer> entry : set){
			JDSku jdSku = new JDSku();
			jdSku.setSku_id(entry.getKey());
			jdSku.setBuynumber(entry.getValue());
			skus.add(jdSku);
		}
		JDSkuService.getInstance().removeStock(skus);
		JDSkuService.getInstance().refreshImportantProperty();
		response.sendRedirect("qukucun.jsp");
	}
}
