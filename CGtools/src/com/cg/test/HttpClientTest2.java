package com.cg.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpClientTest2 hct = new HttpClientTest2();
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setCookiePolicy(CookiePolicy.RFC_2965);
		hct.accessLoginPage(httpClient);
		hct.login(httpClient);
		hct.accessOrderList(httpClient);
	}
	
	private void accessLoginPage(HttpClient httpClient){
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod("http://passport.360buy.com/new/login.aspx?ltype=logout");
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		getMethod.setRequestHeader("Cookie", "alc=+p+kFJ5Ma1S3pOxqcOR8DA==; __jda=97936925.1662277185.1360934608.1360934608.1360934608.1; __jdb=97936925.8.1662277185|1.1360934608; __jdc=97936925; __jdv=97936925|direct|-|none|-; base_domain_5bb57d022ae942988ddb057ffc02b50a=360buy.com; mp=tntzyf%40126.com; rpin=jd_4b53338227dc9; np100=n01; _ghis=%2CilfZ%2Cj7Lq%2CkA2L%2C; _ghit=.878.688.745.; unick=jd_tntzyf; xnsetting_5bb57d022ae942988ddb057ffc02b50a=%7B%22connectState%22%3A2%2C%22oneLineStorySetting%22%3A3%2C%22shortStorySetting%22%3A3%2C%22shareAuth%22%3Anull%7D; pin=jd_4b53338227dc9; ceshi3.com=0C67058F0AD7A9E1FA882F01FD8953880DDF3EFBE59158FEF10A753E44B5933A0CBB7C623B2A0C4242A3FC196DB67B9D4443938E7D043397AFA4502439312C032AE01C7A80B508BD4C1DDF1F8BB975DC8E6DB97699D3A1C5277320EDBD30B458F04076DDD11A209F238E6618632FA25C6C677B3E6C810ECE32146B02531C87DB0ECD6F2E056A8F3675D20EEB21BED516B35071C6CFB331700094AD3D66603F9C");
		
//		httpClient.getState().addCookies(getCookie());
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
//			System.out.println(new String(responseBody,"GBK"));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
	}
	
	private void login(HttpClient client){
	    client.getHostConfiguration().setHost("passport.360buy.com",80); 
	    //登录 
	    PostMethod post = new PostMethod( 
	        "http://passport.360buy.com/uc/loginService?uuid=7db63ffd-6bab-4b08-a8af-e716de13089d&ltype=logout&r=0.521324481972002"); 
	    NameValuePair username = new NameValuePair("loginname", "tntzyf@126.com"); 
	    NameValuePair password = new NameValuePair("loginpwd", "123qweASD"); 
	    NameValuePair authcode = new NameValuePair("authcode", ""); 
	    NameValuePair chkRememberUsername = new NameValuePair("chkRememberUsername", "on"); 
	    post.setRequestBody(new NameValuePair[] { username, password,authcode,chkRememberUsername }); 
	    try {
			client.executeMethod(post);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    String responseString = null;
		try {
			responseString = new String(post.getResponseBodyAsString().getBytes( 
			    "gbk"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    System.out 
	        .println("=========================登录页面==========================="); 
	    System.out.println(responseString); 
	    Cookie[] cookies = client.getState().getCookies(); 
	    client.getState().addCookies(cookies); 
	    post.releaseConnection(); 
	}
	
	private void accessOrderList(HttpClient httpClient){
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod("http://jd2008.360buy.com/JdHome/OrderList.aspx");
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: "
						+ getMethod.getStatusLine());
			}
			// 读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 处理内容
			System.out.println(new String(responseBody,"GBK"));
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}
	}
	
	private static void printStream(InputStream is) {
		BufferedInputStream bis = new BufferedInputStream(is);
		byte[] bufferArr = new byte[128];
		try {
			while (bis.read(bufferArr) > 0) {
				System.out.println(new String(bufferArr, "GBK"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
