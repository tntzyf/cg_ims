package com.cg.test;

import java.util.List;

import com.cg.domain.JDSku;
import com.cg.service.JDSkuService;

public class TestJDSKU {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		updateJDSkuPrice();
	}
	
	public static void add(){
		JDSku sku = new JDSku();
		sku.setBuynumber(3);
		sku.setImportant(true);
		sku.setInstock(true);
		sku.setName("123");
		sku.setPrice(123.34f);
		sku.setSku_id("112");
		JDSkuService.getInstance().addJDSku(sku);
	}
	
	public static void find(){
		JDSku sku = JDSkuService.getInstance().findJDSku("111");
		System.out.println(sku.getPrice());
	}

	public static void query(){
//		List<JDSku> skus = JDSkuService.getInstance().queryJDSkus("1", null, true, null,null,null,null);
//		System.out.println(skus.size());
	}
	public static void remove(){
		JDSkuService.getInstance().removeJDSku("112");
	}
	public static void updateJDSkuPrice(){
	}
	
}
