package com.af.arabimotors.model.request;

public class AdvancedSearchRequest {

	private String price;
	private String year;
	private String model;
	private String conditionType;
	private String fuelType;
	private String gearType;
	private String bodyType;
	private String adTitle;
	
	
	public String getAdTitle() {
		return adTitle;
	}
	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getGearType() {
		return gearType;
	}
	public void setGearType(String gearType) {
		this.gearType = gearType;
	}
	public String getBodyType() {
		return bodyType;
	}
	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}
	public String getConditionType() {
		return conditionType;
	}
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
	@Override
	public String toString() {
		return "AdvancedSearchRequest [price=" + price + ", year=" + year + ", model=" + model + ", conditionType="
				+ conditionType + ", fuelType=" + fuelType + ", gearType=" + gearType + ", bodyType=" + bodyType + "]";
	}
	
	
	
}
