package com.af.arabimotors.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vehicles")
public class VehiclesEntity {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ad_title")
	private String ad_title;
	
	@Column(name = "price", nullable = false)
	private String price;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "condition_type", referencedColumnName = "id")
	private ConditionsEntity vehicle_type;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "body_type", referencedColumnName = "id")
	private BodyTypeEntity bodyTypeEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "model", referencedColumnName = "id")
	private VehicleModelsEntity vehicleModelsEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuel_type", referencedColumnName = "id")
	private FuelTypeEntity fuelTypeEntity;
	
	@Column(name = "mileage", nullable = false)
	private String mileage;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gear_type", referencedColumnName = "id")
	private GearTypeEntity gearTypeEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "engine_capicity", nullable = false)
	private EngineCapicityEntity engineCapicityEntity;
	
	@Column(name = "main_image", nullable = false)
	private String main_image;
	
	@OneToMany(mappedBy = "vehiclesEntity", cascade = CascadeType.ALL)
	private List<VehicleImagesEntity> vehicleImagesEntity;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity userEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "year", referencedColumnName = "id")
	private YearsEntity yearsEntities;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city", referencedColumnName = "id")
	private CityEntity cityEntity;
	
	@Column(name = "vehicle_features", nullable = true)
	private String vehicleFeatures;
	
	@Column(name = "is_featured", columnDefinition = "boolean default false", nullable = false)
	private boolean isFeagtured;
	
	@Column(name = "is_most_popular", columnDefinition = "boolean default false", nullable = false)
	private boolean isMostPopular;

	 @Temporal(TemporalType.TIMESTAMP)
	 private Date createdDate;
	 
	 @Column(name = "views", nullable = false, columnDefinition = "Integer default 0")
	 private Integer views;
	
	 
	 
	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAd_title() {
		return ad_title;
	}

	public void setAd_title(String ad_title) {
		this.ad_title = ad_title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public ConditionsEntity getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(ConditionsEntity vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public BodyTypeEntity getBodyTypeEntity() {
		return bodyTypeEntity;
	}

	public void setBodyTypeEntity(BodyTypeEntity bodyTypeEntity) {
		this.bodyTypeEntity = bodyTypeEntity;
	}

	public VehicleModelsEntity getVehicleModelsEntity() {
		return vehicleModelsEntity;
	}

	public void setVehicleModelsEntity(VehicleModelsEntity vehicleModelsEntity) {
		this.vehicleModelsEntity = vehicleModelsEntity;
	}

	public FuelTypeEntity getFuelTypeEntity() {
		return fuelTypeEntity;
	}

	public void setFuelTypeEntity(FuelTypeEntity fuelTypeEntity) {
		this.fuelTypeEntity = fuelTypeEntity;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public GearTypeEntity getGearTypeEntity() {
		return gearTypeEntity;
	}

	public void setGearTypeEntity(GearTypeEntity gearTypeEntity) {
		this.gearTypeEntity = gearTypeEntity;
	}

	public EngineCapicityEntity getEngineCapicityEntity() {
		return engineCapicityEntity;
	}

	public void setEngineCapicityEntity(EngineCapicityEntity engineCapicityEntity) {
		this.engineCapicityEntity = engineCapicityEntity;
	}

	public String getMain_image() {
		return main_image;
	}

	public void setMain_image(String main_image) {
		this.main_image = main_image;
	}

	public List<VehicleImagesEntity> getVehicleImagesEntity() {
		return vehicleImagesEntity;
	}

	public void setVehicleImagesEntity(List<VehicleImagesEntity> vehicleImagesEntity) {
		this.vehicleImagesEntity = vehicleImagesEntity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public YearsEntity getYearsEntities() {
		return yearsEntities;
	}

	public void setYearsEntities(YearsEntity yearsEntities) {
		this.yearsEntities = yearsEntities;
	}

	public CityEntity getCityEntity() {
		return cityEntity;
	}

	public void setCityEntity(CityEntity cityEntity) {
		this.cityEntity = cityEntity;
	}

	public String getVehicleFeatures() {
		return vehicleFeatures;
	}

	public void setVehicleFeatures(String vehicleFeatures) {
		this.vehicleFeatures = vehicleFeatures;
	}

	public boolean isFeagtured() {
		return isFeagtured;
	}

	public void setFeagtured(boolean isFeagtured) {
		this.isFeagtured = isFeagtured;
	}

	public boolean isMostPopular() {
		return isMostPopular;
	}

	public void setMostPopular(boolean isMostPopular) {
		this.isMostPopular = isMostPopular;
	}

	@Override
	public String toString() {
		return "VehiclesEntity [id=" + id + ", ad_title=" + ad_title + ", price=" + price + ", vehicle_type="
				+ vehicle_type + ", bodyTypeEntity=" + bodyTypeEntity + ", vehicleModelsEntity=" + vehicleModelsEntity
				+ ", fuelTypeEntity=" + fuelTypeEntity + ", mileage=" + mileage + ", gearTypeEntity=" + gearTypeEntity
				+ ", engineCapicityEntity=" + engineCapicityEntity + ", main_image=" + main_image
				+ ", vehicleImagesEntity=" + vehicleImagesEntity + ", description=" + description + ", userEntity="
				+ userEntity + ", yearsEntities=" + yearsEntities + ", cityEntity=" + cityEntity + ", vehicleFeatures="
				+ vehicleFeatures + ", isFeagtured=" + isFeagtured + ", isMostPopular=" + isMostPopular
				+ ", createdDate=" + createdDate + "]";
	}

	

	
}












