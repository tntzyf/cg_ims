package com.cg.service.remoteservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

public abstract class AmazonRemoteInfoServiceAbstract extends RemoteInfoServiceAbstract{
	public abstract String getCountryCode();
}
