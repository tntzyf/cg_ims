package com.cg.service.remoteservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.domain.RemoteGeneratorInfoEntity;
import com.cg.domain.RemoteSku;
import com.cg.exception.CGexception;
import com.cg.util.HttpClientHelper;

public abstract class RemoteInfoServiceAbstract implements RemoteInfoServiceIF{
	
	final static int sockTimeOut = 7000;
	final static int connectTimeOut = 7000;
	
	public RemoteGeneratorInfoEntity generateRemoteInfo(List<String> skuId) {
		List<RemoteSku> jdRemoteSkuList = new ArrayList<RemoteSku>();
		List<String> errorMessages = new ArrayList<String>();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		for(String jdid : skuId){
			RemoteSku jdRemoteSku = new RemoteSku();
			jdRemoteSku.setSkuId(jdid);
			try {
				String price = getPrice(httpclient, jdid);
				jdRemoteSku.setPrice(price);
			} catch (Exception e) {
				errorMessages.add(jdid + " 获取价格信息出错");
				continue;
			} 
			try {
				Boolean stock = hasStock(httpclient, jdid);
				jdRemoteSku.setHasStock(stock);
			} catch (Exception e) {
				errorMessages.add(jdid + " 获取价格信息出错");
			} 
			jdRemoteSkuList.add(jdRemoteSku);
		}
		RemoteGeneratorInfoEntity remoteGeneratorInfoEntity = new RemoteGeneratorInfoEntity();
		remoteGeneratorInfoEntity.setErrorMessages(errorMessages);
		remoteGeneratorInfoEntity.setRemoteSkus(jdRemoteSkuList);
		return remoteGeneratorInfoEntity;
		
	}
	
	public String getPrice(CloseableHttpClient httpclient, String skuId) {
		String price = null;
		for(int i = 0; i<3; i ++){
			price = getPriceOnce(httpclient, skuId);
			if(!StringUtils.isBlank(price)){
				return price;
			}
		}
		return price;
	}

	public Boolean hasStock(CloseableHttpClient httpclient, String skuId) {
		Boolean stock = null;
		for(int i = 0; i<3; i ++){
			stock = getStockOnce(httpclient, skuId);
			if(stock!=null){
				return stock;
			}
		}
		return stock;
	}
	
	protected abstract String getPDPUrlForPrice(String id);
	
	protected abstract String getPDPUrlForStock(String id);
	
	protected abstract String getHostName();
	
	protected abstract String processPrice(String pdpDetail);
	
	protected abstract Boolean processStock(String pdpDetail);

	private String getPriceOnce(CloseableHttpClient httpclient, String jdid) {
		if(httpclient == null){
			httpclient = HttpClients.createDefault();
		}
//		HttpGet priceGet = new HttpGet("http://p.3.cn/prices/get?skuid=J_"+jdid+"&type=1&area=22_1930_50944&callback=cnp");
		HttpGet priceGet = new HttpGet(getPDPUrlForPrice(jdid));
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(sockTimeOut).setConnectTimeout(connectTimeOut).build();
		priceGet.setConfig(requestConfig);
//		HttpClientHelper.setHeaders(priceGet,"http://jd.com");
		if(!StringUtils.isBlank(getHostName())){
			HttpClientHelper.setHeaders(priceGet,getHostName());
		}
		CloseableHttpResponse priceResponse;
		String priceText = "";
		try {
			priceResponse = httpclient.execute(priceGet);
			priceText = EntityUtils.toString(priceResponse.getEntity());
		} catch (Exception e) {
			throw new CGexception(e);
		} finally{
			priceGet.releaseConnection();
		}
//		String price = StringUtils.substringBetween(priceText, "\"p\":\"", "\"");
		String price = processPrice(priceText);
		return price;
	}
	
	public String getHttpContent(String url){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet priceGet = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(sockTimeOut).setConnectTimeout(connectTimeOut).build();
		priceGet.setConfig(requestConfig);
		if(!StringUtils.isBlank(getHostName())){
			HttpClientHelper.setHeaders(priceGet,getHostName());
		}
		CloseableHttpResponse priceResponse;
		String content = "";
		try {
			priceResponse = httpclient.execute(priceGet);
			content = EntityUtils.toString(priceResponse.getEntity());
		} catch (Exception e) {
			throw new CGexception(e);
		} finally{
			priceGet.releaseConnection();
		}
		return content;
	}
	
	protected Boolean getStockOnce(CloseableHttpClient httpclient, String jdid){
		if(httpclient == null){
			httpclient = HttpClients.createDefault();
		}
		httpclient = HttpClients.createDefault();
//		HttpGet pdpGet = new HttpGet("http://item.jd.com/"+jdid+".html");
		if(getPDPUrlForStock(jdid) == null){
			return true;
		}
		HttpGet pdpGet = new HttpGet(getPDPUrlForStock(jdid));
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(sockTimeOut).setConnectTimeout(connectTimeOut).build();
		pdpGet.setConfig(requestConfig);
		HttpClientHelper.setHeaders(pdpGet,getHostName());
		CloseableHttpResponse pdpResponse;
		String pdpText = "";
		try {
			pdpResponse = httpclient.execute(pdpGet);
			pdpText = EntityUtils.toString(pdpResponse.getEntity());
			pdpText = new String(pdpText.getBytes("ISO-8859-1"),"GBK");
		} catch (Exception e) {
			throw new CGexception(e);
		} finally{
			pdpGet.releaseConnection();
		}
		
		return processStock(pdpText);
//		String skuIdKey = StringUtils.substringBetween(pdpText, "skuidkey:'", "'");
//		if(StringUtils.isBlank(skuIdKey)){
//			skuIdKey = StringUtils.substringBetween(pdpText, "sid: \"", "\"");
//		}
//		System.out.println(skuIdKey);
//		HttpGet stockGet = new HttpGet("http://st.3.cn/gds.html?callback=getStockCallback&skuid="+skuIdKey+"&provinceid=22&cityid=1930&areaid=50944&townid=0");
//		HttpClientHelper.setHeaders(stockGet,"http://jd.com");
//		CloseableHttpResponse stockResponse;
//		String stockText = "";
//		try {
//			stockResponse = httpclient.execute(stockGet);
//			stockText = EntityUtils.toString(stockResponse.getEntity());
//		} catch (Exception e) {
//			throw new CGexception(e);
//		}
//		String stockName = StringUtils.substringBetween(stockText, "StockStateName\":\"", "\"");
//		
//		return stockName;
	}
	
	public String transmitPrice(String priceStr){
		if(!StringUtils.isBlank(priceStr)){
			String price = StringUtils.trim(priceStr);
			return StringUtils.replace(price, ",", ".");
		}
		return priceStr;
	}
}
