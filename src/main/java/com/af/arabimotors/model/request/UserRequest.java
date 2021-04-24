package com.af.arabimotors.model.request;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.entities.SellerTypeEntity;

public class UserRequest {

	@NotBlank(message = "{email.notempty}")
	private String email;
	@NotBlank(message = "{fullname.notemtpy}")
	private String fullname;
	@NotBlank(message = "{address.notempty}")
	private String address;
	private String password;
	@NotBlank(message = "{phone.notempty}")
	private String phone;
	private CityEntity city;
	private SellerTypeEntity sellerTypeEntity;
	private String confirm_password;
	private MultipartFile user_photo;
	private Long id;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getConfirm_password() {
		return confirm_password;
	}
	public void setConfirm_password(String confirm_password) {
		this.confirm_password = confirm_password;
	}
	public MultipartFile getUser_photo() {
		return user_photo;
	}
	public void setUser_photo(MultipartFile user_photo) {
		this.user_photo = user_photo;
	}

	
	
	
}
