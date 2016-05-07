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

public class AmazonCNRemoteInfoService extends AmazonRemoteInfoServiceAbstract {

	@Override
	protected String getHostName() {
		return "http://www.amazon.cn/";
	}

	@Override
	protected String getPDPUrlForPrice(String id) {
		return "http://www.amazon.cn/dp/"+id;
	}

	@Override
	protected String getPDPUrlForStock(String id) {
		return null;
//		return "http://www.amazon.cn/dp/"+id;
	}

	@Override
	protected String processPrice(String pdpDetail) {
		String price = StringUtils.substringBetween(pdpDetail, "<b class=\"priceLarge\">￥ ", "</b>");
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
		return "CN";
	}
	
	
}
