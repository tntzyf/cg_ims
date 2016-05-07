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

public class TestAmazonUKSKU {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet priceGet = new HttpGet("http://astore.amazon.co.uk/7080ren-21/detail/B002GDM2S2");
		CloseableHttpResponse priceResponse;
		String priceText = "";
		try {
			priceResponse = httpclient.execute(priceGet);
			priceText = EntityUtils.toString(priceResponse.getEntity());
		} catch (Exception e) {
			throw new CGexception(e);
		}
		
		String price = StringUtils.substringBetween(priceText, "<span class=\"amount\" id=\"detailOfferPrice\">&pound;", "</span>");
		System.out.println(price);
		
	}
	
	
	
}
