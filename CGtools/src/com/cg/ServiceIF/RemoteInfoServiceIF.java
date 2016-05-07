package com.cg.ServiceIF;

import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;

import com.cg.domain.RemoteGeneratorInfoEntity;

public interface RemoteInfoServiceIF {
	public String getPrice(CloseableHttpClient httpclient, String skuId);
	public Boolean hasStock(CloseableHttpClient httpclient, String skuId);
	public RemoteGeneratorInfoEntity generateRemoteInfo(List<String> skuId);
	public String getPromotionInfo(String skuId);
	
}
