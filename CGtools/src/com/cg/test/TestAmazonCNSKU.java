package com.cg.test;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cg.domain.JDSku;
import com.cg.exception.CGexception;
import com.cg.service.JDSkuService;
import com.cg.service.remoteservice.AmazonCNRemoteInfoService;
import com.cg.service.remoteservice.RemoteInfoServiceAbstract;

public class TestAmazonCNSKU {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RemoteInfoServiceAbstract t = new AmazonCNRemoteInfoService();
		System.out.println(t.getPrice(httpclient, "B0060RNIX8"));
	}
	
	
	
}
