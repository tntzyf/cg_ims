package com.cg.domain;

public class AmazonItemPrice {
	private String skuId;
	private String countryCode;
	private Double price;
	
	public AmazonItemPrice(){}
	
	public AmazonItemPrice(String countryCode, Double price) {
		super();
		this.countryCode = countryCode;
		this.price = price;
	}

	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSkuId() {
		return skuId;
	}

	public AmazonItemPrice(String skuId, String countryCode, Double price) {
		super();
		this.skuId = skuId;
		this.countryCode = countryCode;
		this.price = price;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
}
