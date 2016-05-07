package com.cg.util;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.http.client.methods.HttpGet;

public class HttpClientHelper {

	public static void setHeaders(HttpGet method,String referer) {
		method.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;");
		method.setHeader("Accept-Language", "zh-cn");
		method
				.setHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
		method.setHeader("Accept-Charset", "UTF-8");
		method.setHeader("Keep-Alive", "300");
		method.setHeader("Connection", "Keep-Alive");
		method.setHeader("Cache-Control", "no-cache");
		method.setHeader("referer", referer);
	}
}
