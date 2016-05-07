package com.cg.test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
//
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.CookieStore;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpUriRequest;
//import org.apache.http.client.params.ClientPNames;
//import org.apache.http.client.params.CookiePolicy;
//import org.apache.http.client.protocol.ClientContext;
//import org.apache.http.impl.client.BasicCookieStore;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.cookie.BasicClientCookie;
//import org.apache.http.protocol.BasicHttpContext;
//import org.apache.http.protocol.HttpContext;

public class HttpClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// 核心应用类
//		HttpClient httpClient = new DefaultHttpClient();
//		httpClient.getParams().setParameter(
//                ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
//		// HTTP请求
//		HttpUriRequest request = new HttpGet(
//				"http://passport.360buy.com/new/login.aspx?ltype=logout");
//
//		
//		// 打印请求信息
//		System.out.println(request.getRequestLine());
//		HttpContext localContext = new BasicHttpContext();
//		localContext.setAttribute(ClientContext.COOKIE_STORE, getCookieStore());
//		
//		try {
//			// 发送请求，返回响应
//			HttpResponse response = httpClient.execute(request,localContext);
//
//			Header[] headers = response.getAllHeaders();
//			for(Header header : headers){
//				System.out.println(header.getName() + " : " + header.getValue());
//			}
//			// 打印响应信息
//			HttpEntity entity = response.getEntity();
//			InputStream is = entity.getContent();
//			
//			printStream(is);
//			System.out.println(response.getStatusLine());
//		} catch (ClientProtocolException e) {
//			// 协议错误
//			e.printStackTrace();
//		} catch (IOException e) {
//			// 网络异常
//			e.printStackTrace();
//		}
	}
	
//	private static CookieStore getCookieStore(){
//		String a = "alc=u3jVOyIdpF52JpZzMjKxsw==; mp=tntzyf; __jda=97936925.785346572.1360911407.1360911407.1360911407.1; __jdb=97936925.5.785346572|1.1360911407; __jdc=97936925; __jdv=97936925|direct|-|none|-; base_domain_5bb57d022ae942988ddb057ffc02b50a=360buy.com; xnsetting_5bb57d022ae942988ddb057ffc02b50a=%7B%22connectState%22%3A2%2C%22oneLineStorySetting%22%3A3%2C%22shortStorySetting%22%3A3%2C%22shareAuth%22%3Anull%7D";
//		String[] nvs = a.split(";");
//		CookieStore cookieStore = new BasicCookieStore();
//		for(String nv : nvs){
//			int i = nv.indexOf("=");
//			BasicClientCookie b = new BasicClientCookie(nv.substring(0, i).trim(), nv.substring(i+1));
//			cookieStore.addCookie(b);
//		}
//		return cookieStore;
//	}
//	
	private static void printStream(InputStream is){
		BufferedInputStream bis = new BufferedInputStream(is);
		byte[] bufferArr = new byte[128];
		try{
			while(bis.read(bufferArr)>0){
				System.out.println(new String(bufferArr,"GBK"));
			}
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bis!=null){
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
