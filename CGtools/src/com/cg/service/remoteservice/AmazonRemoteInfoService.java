package com.cg.service.remoteservice;

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
import org.apache.log4j.helpers.ThreadLocalMap;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.domain.JDInfo;
import com.cg.domain.RemoteGeneratorInfoEntity;
import com.cg.domain.RemoteSku;
import com.cg.exception.CGexception;

public class AmazonRemoteInfoService extends AmazonRemoteInfoServiceAbstract {

	private Map<String,String> stockTempMap = new HashMap<String, String>();
	
//	public String getAmazonPriceOnce(CloseableHttpClient httpclient, String id) {
//		if(httpclient == null){
//			httpclient = HttpClients.createDefault();
//		}
//		HttpGet priceGet = new HttpGet("http://astore.amazon.com/conexant-20/detail/"+id);
//		CloseableHttpResponse priceResponse;
//		String pdpText = "";
//		try {
//			priceResponse = httpclient.execute(priceGet);
//			pdpText = EntityUtils.toString(priceResponse.getEntity());
//		} catch (Exception e) {
//			throw new CGexception(e);
//		}
//		String price = StringUtils.substringBetween(pdpText, "<span class=\"amount\" id=\"detailOfferPrice\">$", "</span>");
//		if(!StringUtils.isBlank(price)){
//			price = StringUtils.trim(price);
//		}
//		return price;
//	}
	
//	public String getAmazonStockOnce(CloseableHttpClient httpclient, String id){
//		if(true){
//			return null;
//		}
//		if(stockTempMap.containsKey(id)){
//			String stock = stockTempMap.get(id);
//			stockTempMap.remove(id);
//			return stock;
//		}
//		if(httpclient == null){
//			httpclient = HttpClients.createDefault();
//		}
//		httpclient = HttpClients.createDefault();
//		HttpGet stockGet = new HttpGet("http://www.amazon.com/gp/product/"+id);
//		CloseableHttpResponse pdpResponse;
//		String pdpText = "";
//		try {
//			pdpResponse = httpclient.execute(stockGet);
//			pdpText = EntityUtils.toString(pdpResponse.getEntity());
//		} catch (Exception e) {
//			throw new CGexception(e);
//		}
//		
//		String stock = StringUtils.substringBetween(pdpText, "a-size-medium a-color-success\">", "</span>");
//		if(StringUtils.isBlank(stock)){
//			stock = StringUtils.trim(stock);
//		}
//		return stock;
//	}

	@Override
	protected String getHostName() {
		return "http://astore.amazon.com/";
	}

	@Override
	protected String getPDPUrlForPrice(String id) {
		return "http://astore.amazon.com/conexant-20/detail/"+id;
	}

	@Override
	protected String getPDPUrlForStock(String id) {
//		return "http://www.amazon.com/gp/product/"+id;
		return null;
	}

	@Override
	protected String processPrice(String pdpDetail) {
		String price = StringUtils.substringBetween(pdpDetail, "<span class=\"amount\" id=\"detailOfferPrice\">$", "</span>");
		if(!StringUtils.isBlank(price)){
			price = StringUtils.trim(price);
		}
		return price;
	}

	@Override
	protected Boolean processStock(String pdpDetail) {
		return true;
	}

	public String getPromotionInfo(String skuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCountryCode() {
		// TODO Auto-generated method stub
		return "US";
	}
	
	
}
