package com.cg.domain;

public class JDSku extends BaseSku{
	private String sku_id;
	private String name;
	private Float price;
	private Boolean important;
	private Boolean instock;
	private Integer buynumber;
	private Float publishingPrice;
	private String updateDate;
	private String typeId;
	private String special_type;
	private String barcode;
	
	public String getSpecial_type() {
		return special_type;
	}
	public void setSpecial_type(String specialType) {
		special_type = specialType;
	}
	public String getSku_id() {
		return sku_id;
	}
	public void setSku_id(String skuId) {
		sku_id = skuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Boolean getImportant() {
		return important;
	}
	public void setImportant(Boolean important) {
		this.important = important;
	}
	public Boolean getInstock() {
		return instock;
	}
	public void setInstock(Boolean instock) {
		this.instock = instock;
	}
	public Integer getBuynumber() {
		return buynumber;
	}
	public void setBuynumber(Integer buynumber) {
		this.buynumber = buynumber;
	}
	public Float getPublishingPrice() {
		return publishingPrice;
	}
	public void setPublishingPrice(Float publishingPrice) {
		this.publishingPrice = publishingPrice;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
}
