package com.cg.service.remoteservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.domain.JDInfo;
import com.cg.domain.RemoteGeneratorInfoEntity;
import com.cg.domain.RemoteSku;
import com.cg.exception.CGexception;
import com.cg.util.HttpClientHelper;

public class JDRemoteInfoService extends RemoteInfoServiceAbstract {

//	public String getPrice(CloseableHttpClient httpclient, String skuId) {
//		String price = null;
//		for(int i = 0; i<3; i ++){
//			price = getJDPriceOnce(httpclient, skuId);
//			if(!StringUtils.isBlank(price)){
//				return price;
//			}
//		}
//		return price;
//	}
//
//	public String getStock(CloseableHttpClient httpclient, String skuId) {
//		String stock = null;
//		for(int i = 0; i<3; i ++){
//			stock = getJDStockOnce(httpclient, skuId);
//			if(!StringUtils.isBlank(stock)){
//				return stock;
//			}
//		}
//		return stock;
//	}

	public String getJDPriceOnce(CloseableHttpClient httpclient, String jdid) {
		if(httpclient == null){
			httpclient = HttpClients.createDefault();
		}
		
		HttpGet priceGet = new HttpGet("http://p.3.cn/prices/get?skuid=J_"+jdid+"&type=1&area=22_1930_50944&callback=cnp");
		HttpClientHelper.setHeaders(priceGet,"http://jd.com");
		CloseableHttpResponse priceResponse;
		String priceText = "";
		try {
			priceResponse = httpclient.execute(priceGet);
			priceText = EntityUtils.toString(priceResponse.getEntity());
		} catch (Exception e) {
			throw new CGexception(e);
		}
		String price = StringUtils.substringBetween(priceText, "\"p\":\"", "\"");
		return price;
	}
	
	public String getJDStockOnce(CloseableHttpClient httpclient, String jdid){
		if(httpclient == null){
			httpclient = HttpClients.createDefault();
		}
		httpclient = HttpClients.createDefault();
		HttpGet pdpGet = new HttpGet("http://c0.3.cn/stock?skuId="+ jdid +"&venderId=0&cat=4052,4085,4318&area=22_1930_50944_0&buyNum=1&extraParam={%22originid%22:%221%22}&ch=1");
		HttpClientHelper.setHeaders(pdpGet,"http://jd.com");
		CloseableHttpResponse pdpResponse;
		String stockText = "";
		try {
			pdpResponse = httpclient.execute(pdpGet);
			stockText = EntityUtils.toString(pdpResponse.getEntity());
		} catch (Exception e) {
			throw new CGexception(e);
		}
		
		String stockName = StringUtils.substringBetween(stockText, "StockStateName\":\"", "\"");
		
		return stockName;
	}

	@Override
	protected String getHostName() {
		return "http://jd.com";
	}

	@Override
	protected String getPDPUrlForPrice(String id) {
		return "http://p.3.cn/prices/get?type=1&area=22_1930_50944&pdtk=&pduid=514225394&pdpin=&pdbp=0&skuid=J_"+id;
	}

	@Override
	protected String getPDPUrlForStock(String id) {
		// TODO Auto-generated method stub
		return "http://c0.3.cn/stock?skuId="+id+"&venderId=0&cat=4052,4085,4318&area=22_1930_50944_0&buyNum=1&ch=1&extraParam=%7b%22originid%22:%221%22%7d";
	}

	@Override
	protected String processPrice(String pdpDetail) {
		String price = StringUtils.substringBetween(pdpDetail, "\"p\":\"", "\"");
		return price;
	}

	@Override
	protected Boolean processStock(String pdpDetail) {
		String stockName = StringUtils.substringBetween(pdpDetail, "StockStateName\":\"", "\"");
		if("现货".equals(stockName)|| "有货".equals(stockName)||"预订".equals(stockName) || "可配货".equals(stockName)){
			return true;
		}else if("无货".equals(stockName)){
			return false;
		}
		return null;
	}
	
	public String getPromotionInfo(String skuId){
		String url = "http://jprice.360buy.com/pageadword/"+skuId+"-1-1-1_72_2799_0.html?callback=Promotions.set";
		String content = this.getHttpContent(url);
		content = StringUtils.deleteWhitespace(content);
		content = StringUtils.substringBetween(content,"Promotions.set(", ");");
		JSONObject jsonO = JSONObject.fromObject(content);
		JSONArray promotionInfoList = (JSONArray)jsonO.get("promotionInfoList");
		if(promotionInfoList.isEmpty()){
			return null;
		}
		JSONObject promotionInfo = (JSONObject)promotionInfoList.get(0);
		if(promotionInfo.get("promoType")==null){
			return null;
		}
		if(!"10".equals(promotionInfo.get("promoType").toString())){
			return null;
		}
		JSONArray fullLadderDiscountList = (JSONArray)promotionInfo.get("fullLadderDiscountList");
		if(fullLadderDiscountList==null||fullLadderDiscountList.isEmpty()){
			return null;
		}
		JSONObject fullLadderDiscount = (JSONObject)fullLadderDiscountList.get(0);
		String needMoney = fullLadderDiscount.getString("needMoney");
		String rewardMoney = fullLadderDiscount.getString("rewardMoney");
		if(StringUtils.isNotBlank(needMoney)&&StringUtils.isNotBlank(rewardMoney)){
			return (needMoney+"-"+rewardMoney); 
		}
		return null;
	}
}
