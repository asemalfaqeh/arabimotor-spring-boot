package com.af.arabimotors.model.response;

import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.entities.SellerTypeEntity;

public class UserResponse {

	private String email;
	private String fullname;
	private String address;
	private String phone;
	private CityEntity city;
	private SellerTypeEntity sellerTypeEntity;
	private String user_photo;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public CityEntity getCity() {
		return city;
	}
	public void setCity(CityEntity city) {
		this.city = city;
	}
	public SellerTypeEntity getSellerTypeEntity() {
		return sellerTypeEntity;
	}
	public void setSellerTypeEntity(SellerTypeEntity sellerTypeEntity) {
		this.sellerTypeEntity = sellerTypeEntity;
	}
	public String getUser_photo() {
		return user_photo;
	}
	public void setUser_photo(String user_photo) {
		this.user_photo = user_photo;
	}
	@Override
	public String toString() {
		return "UserResponse [email=" + email + ", fullname=" + fullname + ", address=" + address + ", phone=" + phone
				+ ", city=" + city + ", sellerTypeEntity=" + sellerTypeEntity + ", user_photo=" + user_photo + "]";
	}
	
	
	
}
