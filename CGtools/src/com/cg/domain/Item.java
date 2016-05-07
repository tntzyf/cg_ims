package com.cg.domain;

import java.util.Map;

public class Item implements Comparable{
	private float totalPrice;
	private float logicPrice;
	private Map<Sku,Integer> skus;
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public float getLogicPrice() {
		return logicPrice;
	}
	public void setLogicPrice(float logicPrice) {
		this.logicPrice = logicPrice;
	}
	public int compareTo(Object o) {
		if(this.logicPrice<((Item)o).getLogicPrice()){
			return -1;
		}else{
			return 1;
		}
	}
	public Map<Sku, Integer> getSkus() {
		return skus;
	}
	public void setSkus(Map<Sku, Integer> skus) {
		this.skus = skus;
	}
	
}
