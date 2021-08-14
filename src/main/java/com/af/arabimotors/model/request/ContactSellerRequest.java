package com.af.arabimotors.model.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.entities.VehiclesEntity;

public class ContactSellerRequest {

	@NotBlank(message = "يرجى ادخال الاسم الكامل")
	@Size(max = 128, message = "يجب ان لا يتحوي الحقل على اكثر من 128 حرف")
	private String customerName;
	
	@NotBlank(message = "يرجى ادخال البريد الالكتروني")
	@Size(max = 24, message = "يجب ان لا يتحوي الحقل على اكثر من 24 حرف")
	private String customerEmail;
	
	@NotBlank(message = "يرجى ادخال رقم الهاتف المحمول")
	@Size(max = 16, message = "يجب ان لا يحتوي الحقل على اكثر من 16 حرف")
	private String customerPhone;
	
	@NotBlank(message = "حقل الرسالة يجب ان لا يكون فارغ")
	@Size(max = 500, message = "يجب تم لا يحتوي الحقل على اكثر من 500 حرف")
	private String customerMsg;
	
	private UserEntity userEntity;
	
	private VehiclesEntity vehiclesEntity;
	
	private Date createDate;
	
	
	
	
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public VehiclesEntity getVehiclesEntity() {
		return vehiclesEntity;
	}

	public void setVehiclesEntity(VehiclesEntity vehiclesEntity) {
		this.vehiclesEntity = vehiclesEntity;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerMsg() {
		return customerMsg;
	}

	public void setCustomerMsg(String customerMsg) {
		this.customerMsg = customerMsg;
	}
	

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public String toString() {
		return "ContactSellerRequest [customerName=" + customerName + ", customerEmail=" + customerEmail
				+ ", customerPhone=" + customerPhone + ", customerMsg=" + customerMsg + ", userEntity=" + userEntity
				+ ", vehiclesEntity=" + vehiclesEntity + ", createDate=" + createDate + "]";
	}

	
	
	
}
