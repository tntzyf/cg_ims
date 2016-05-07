package com.cg.test;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.service.remoteservice.JDRemoteInfoService;

public class HttpJDSKUPriceTest {
	
	static String a = "20083255";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RemoteInfoServiceIF jdRemoteInfoServiceIF = new JDRemoteInfoService();
		CloseableHttpClient httpclient = HttpClients.createDefault();
//		String price = jdRemoteInfoServiceIF.getPrice(httpclient, a);
//		System.out.println(price);
		Boolean stockStr = jdRemoteInfoServiceIF.hasStock(httpclient, a);
		System.out.println(stockStr);
	}

}
