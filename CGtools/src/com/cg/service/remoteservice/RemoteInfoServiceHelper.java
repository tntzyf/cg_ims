package com.cg.service.remoteservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.domain.JDSku;
public class RemoteInfoServiceHelper {
	private RemoteInfoServiceHelper(){};
	
	private static RemoteInfoServiceHelper remoteInfoServiceHelper = new RemoteInfoServiceHelper();
	
	public static RemoteInfoServiceHelper getInstance(){
		return remoteInfoServiceHelper;
	}
	
	private static AmazonRemoteInfoServiceAbstract amazonCNRemoteInfoService = new  AmazonCNRemoteInfoService();
	private static AmazonRemoteInfoServiceAbstract amazonUKRemoteInfoService = new  AmazonUKRemoteInfoService();
	private static AmazonRemoteInfoServiceAbstract amazonRemoteInfoService = new  AmazonRemoteInfoService();
	private static AmazonRemoteInfoServiceAbstract amazonDERemoteInfoService = new  AmazonDERemoteInfoService();
	private static AmazonRemoteInfoServiceAbstract amazonFRRemoteInfoService = new  AmazonFRRemoteInfoService();
	private static RemoteInfoServiceIF jdRemoteInfoService = new  JDRemoteInfoService();
	private static List<AmazonRemoteInfoServiceAbstract> amazonItemRemoteInfoServices = new ArrayList<AmazonRemoteInfoServiceAbstract>();
	static {
		amazonItemRemoteInfoServices.add(amazonRemoteInfoService);
		amazonItemRemoteInfoServices.add(amazonDERemoteInfoService);
		amazonItemRemoteInfoServices.add(amazonFRRemoteInfoService);
		amazonItemRemoteInfoServices.add(amazonUKRemoteInfoService);
	}
	
	public String getPrice(CloseableHttpClient httpclient, JDSku jdSku){
		RemoteInfoServiceIF remoteInfoService = getRemoteInfoServiceIF(jdSku);
		if(remoteInfoService == null){
			return null;
		}
		return remoteInfoService.getPrice(httpclient, jdSku.getSku_id());
	}
	
	public Boolean hasStock(CloseableHttpClient httpclient, JDSku jdSku){
		RemoteInfoServiceIF remoteInfoService = getRemoteInfoServiceIF(jdSku);
		if(remoteInfoService == null){
			return null;
		}
		return remoteInfoService.hasStock(httpclient, jdSku.getSku_id());
	}
	
	public static RemoteInfoServiceIF getRemoteInfoServiceIF(JDSku jdSku){
		if("1".equals(jdSku.getTypeId())||"2".equals(jdSku.getTypeId())||"3".equals(jdSku.getTypeId())){
			return jdRemoteInfoService;
		} else if("4".equals(jdSku.getTypeId())||"5".equals(jdSku.getTypeId())){
			return amazonCNRemoteInfoService;
		} else if("6".equals(jdSku.getTypeId())){
			return amazonRemoteInfoService;
		} else if("7".equals(jdSku.getTypeId())){
			return amazonUKRemoteInfoService;
		} 
		return null;
	}
	
	public static List<AmazonRemoteInfoServiceAbstract> getAmazonItemRemoteInfoServices(){
		return amazonItemRemoteInfoServices;
	}
}
