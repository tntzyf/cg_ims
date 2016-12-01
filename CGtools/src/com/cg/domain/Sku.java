package com.cg.domain;

public class Sku implements Comparable,Cloneable{
	private String title;
	private String jdid;
	private float price;
	private boolean sale;
	private boolean hasstock;
	private Float publishPrice;
	private String cgid;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isSale() {
		return sale;
	}
	public void setSale(boolean sale) {
		this.sale = sale;
	}
	
	public int compareTo(Object o) {
		if(!this.isSale() && ((Sku)o).isSale()){
			return 1;
		}
		if(this.isSale() && !((Sku)o).isSale()){
			return -1;
		}
		if(this.price<((Sku)o).getPrice()){
			return 1;
		}else{
			return -1;
		}
	}
	@Override
	protected Sku clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Sku)super.clone();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title;
	}
	public String getJdid() {
		return jdid;
	}
	public void setJdid(String jdid) {
		this.jdid = jdid;
	}
	public boolean isHasstock() {
		return hasstock;
	}
	public void setHasstock(boolean hasstock) {
		this.hasstock = hasstock;
	}
	public Float getPublishPrice() {
		return publishPrice;
	}
	public void setPublishPrice(Float publishPrice) {
		this.publishPrice = publishPrice;
	}
	public String getCgid() {
		return cgid;
	}
	public void setCgid(String cgid) {
		this.cgid = cgid;
	}
	
	
}
