package com.af.arabimotors.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contact_seller")
public class ContactSellerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "customer_name", nullable = false)
	private String customerName;
	
	@Column(name = "customer_email", nullable = false)
	private String customerEmail;
	
	@Column(name = "customer_phone", nullable = false)
	private String customerPhone;
	
	@Column(name = "customer_msg", nullable = false)
	private String customerMsg;
	
	@OneToOne(fetch  = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id", referencedColumnName = "id")
	private VehiclesEntity vehiclesEntity;
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public VehiclesEntity getVehiclesEntity() {
		return vehiclesEntity;
	}
	public void setVehiclesEntity(VehiclesEntity vehiclesEntity) {
		this.vehiclesEntity = vehiclesEntity;
	}
	
	@Override
	public String toString() {
		return "ContactSellerEntity [id=" + id + ", customerName=" + customerName + ", customerEmail=" + customerEmail
				+ ", customerPhone=" + customerPhone + ", customerMsg=" + customerMsg + "]";
	}
	
	
	
}
