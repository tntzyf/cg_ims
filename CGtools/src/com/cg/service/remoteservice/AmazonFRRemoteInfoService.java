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

public class AmazonFRRemoteInfoService extends AmazonRemoteInfoServiceAbstract {

	private Map<String,String> stockTempMap = new HashMap<String, String>();
	
//	public String getPrice(CloseableHttpClient httpclient, String skuId) {
//		String price = null;
//		for(int i = 0; i<3; i ++){
//			price = getAmazonPriceOnce(httpclient, skuId);
//			if(!StringUtils.isBlank(price)){
//				return price;
//			}
//		}
//		return price;
//	}

	public String getStock(CloseableHttpClient httpclient, String skuId) {
		return null;
	}

//	public String getAmazonPriceOnce(CloseableHttpClient httpclient, String id) {
//		if(httpclient == null){
//			httpclient = HttpClients.createDefault();
//		}
//		HttpGet priceGet = new HttpGet("http://astore.amazon.co.uk/7080ren-21/detail/"+id);
//		CloseableHttpResponse priceResponse;
//		String pdpText = "";
//		try {
//			priceResponse = httpclient.execute(priceGet);
//			pdpText = EntityUtils.toString(priceResponse.getEntity());
//		} catch (Exception e) {
//			throw new CGexception(e);
//		}
//		String price = StringUtils.substringBetween(pdpText, "<span class=\"amount\" id=\"detailOfferPrice\">&pound;", "</span>");
//		if(!StringUtils.isBlank(price)){
//			price = StringUtils.trim(price);
//		}
//		return price;
//	}
	
	public String getAmazonStockOnce(CloseableHttpClient httpclient, String id){
		return null;
	}

	@Override
	protected String getHostName() {
		return "http://www.amazon.de/";
	}

	@Override
	protected String getPDPUrlForPrice(String id) {
		return "http://www.amazon.de/dp/"+id;
	}

	@Override
	protected String getPDPUrlForStock(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String processPrice(String pdpDetail) {
		String price = StringUtils.substringBetween(pdpDetail, "<span id=\"priceblock_ourprice\" class=\"a-size-medium a-color-price\">EUR ", "</span>");
		return this.transmitPrice(price);
	}

	@Override
	protected Boolean processStock(String pdpDetail) {
		// TODO Auto-generated method stub
		return true;
	}

	public String getPromotionInfo(String skuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCountryCode() {
		// TODO Auto-generated method stub
		return "FR";
	}
	
	
}
