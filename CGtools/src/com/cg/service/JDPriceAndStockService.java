package com.cg.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.domain.JDInfo;
import com.cg.service.remoteservice.JDRemoteInfoService;

public class JDPriceAndStockService {

	private JDPriceAndStockService(){};
	
	private static JDPriceAndStockService jDPriceAndStockService = new JDPriceAndStockService();
	
	public static JDPriceAndStockService getInstance(){
		return jDPriceAndStockService;
	}
	
	private RemoteInfoServiceIF jdRemoteInfoServiceIF = new JDRemoteInfoService();
	
//	public Map<String,Object> getJDinfos(List<String> jdids){
//		List<JDInfo> jdInfoList = new ArrayList<JDInfo>();
//		List<String> errorMessages = new ArrayList<String>();
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		for(String jdid : jdids){
//			JDInfo jdinfo = new JDInfo();
//			jdinfo.setJdid(jdid);
//			try {
//				String price = jdRemoteInfoServiceIF.getPrice(httpclient, jdid);
//				jdinfo.setPrice(price);
//			} catch (Exception e) {
//				errorMessages.add(jdid + " 获取价格信息出错");
//			} 
//			try {
//				String stock = jdRemoteInfoServiceIF.getStock(httpclient, jdid);
//				jdinfo.setStock(stock);
//			} catch (Exception e) {
//				errorMessages.add(jdid + " 获取价格信息出错");
//			} 
//			jdInfoList.add(jdinfo);
//		}
//		Map<String,Object> maps = new HashMap<String, Object>();
//		maps.put("jdinfos", jdInfoList);
//		maps.put("errorMessages", errorMessages);
//		return maps;
//	}
	
//	public String getJDPrice(CloseableHttpClient httpclient, String jdid) throws ClientProtocolException, IOException{
//		String price = null;
//		for(int i = 0; i<3; i ++){
//			price = getJDPriceOnce(httpclient, jdid);
//			if(!StringUtils.isBlank(price)){
//				return price;
//			}
//		}
//		return price;
//	}
//	public String getJDPriceOnce(CloseableHttpClient httpclient, String jdid) throws ClientProtocolException, IOException{
//		if(httpclient == null){
//			httpclient = HttpClients.createDefault();
//		}
//		HttpGet priceGet = new HttpGet("http://p.3.cn/prices/get?skuid=J_"+jdid+"&type=1&area=22_1930_50944&callback=cnp");
//		CloseableHttpResponse priceResponse = httpclient.execute(priceGet);
//		String priceText = EntityUtils.toString(priceResponse.getEntity());
//		String price = StringUtils.substringBetween(priceText, "\"p\":\"", "\"");
//		return price;
//	}
//	
//	public String getJDStock(CloseableHttpClient httpclient, String jdid) throws ClientProtocolException, IOException{
//		String stock = null;
//		for(int i = 0; i<3; i ++){
//			stock = getJDStockOnce(httpclient, jdid);
//			if(!StringUtils.isBlank(stock)){
//				return stock;
//			}
//		}
//		return stock;
//	}
//	
//	public String getJDStockOnce(CloseableHttpClient httpclient, String jdid) throws ClientProtocolException, IOException{
//		if(httpclient == null){
//			httpclient = HttpClients.createDefault();
//		}
//		httpclient = HttpClients.createDefault();
//		HttpGet pdpGet = new HttpGet("http://item.jd.com/"+jdid+".html");
//		CloseableHttpResponse pdpResponse = httpclient.execute(pdpGet);
//		String pdpText = EntityUtils.toString(pdpResponse.getEntity());
//		String skuIdKey = StringUtils.substringBetween(pdpText, "skuidkey:'", "'");
//		if(StringUtils.isBlank(skuIdKey)){
//			skuIdKey = StringUtils.substringBetween(pdpText, "sid: \"", "\"");
//		}
//		System.out.println(skuIdKey);
//		HttpGet stockGet = new HttpGet("http://st.3.cn/gds.html?callback=getStockCallback&skuid="+skuIdKey+"&provinceid=22&cityid=1930&areaid=50944&townid=0");
//		CloseableHttpResponse stockResponse = httpclient.execute(stockGet);
//		String stockText = EntityUtils.toString(stockResponse.getEntity());
//		String stockName = StringUtils.substringBetween(stockText, "StockStateName\":\"", "\"");
//		
//		return stockName;
//	}
}
