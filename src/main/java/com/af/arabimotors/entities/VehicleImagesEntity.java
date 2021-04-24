package com.af.arabimotors.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_images")
public class VehicleImagesEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7525304935987971965L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "image", nullable = false)
	private String image;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicles_id")
	private VehiclesEntity vehiclesEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public VehiclesEntity getVehiclesEntity() {
		return vehiclesEntity;
	}

	public void setVehiclesEntity(VehiclesEntity vehiclesEntity) {
		this.vehiclesEntity = vehiclesEntity;
	}

	
	
}
