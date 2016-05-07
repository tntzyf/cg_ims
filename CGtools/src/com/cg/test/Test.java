package com.cg.test;

import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang3.StringUtils;

import com.cg.service.JDSkuService;
import com.cg.service.remoteservice.JDRemoteInfoService;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JDRemoteInfoService a = new JDRemoteInfoService();
		System.out.println(a.getPromotionInfo("20061417"));
	}

}
