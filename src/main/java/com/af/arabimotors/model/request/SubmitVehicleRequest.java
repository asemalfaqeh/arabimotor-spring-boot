package com.af.arabimotors.model.request;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.af.arabimotors.entities.BodyTypeEntity;
import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.entities.ConditionsEntity;
import com.af.arabimotors.entities.EngineCapicityEntity;
import com.af.arabimotors.entities.FuelTypeEntity;
import com.af.arabimotors.entities.GearTypeEntity;
import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.entities.VehicleImagesEntity;
import com.af.arabimotors.entities.VehicleModelsEntity;
import com.af.arabimotors.entities.YearsEntity;


public class SubmitVehicleRequest {

	@NotBlank(message = "{field.not_empty}")
	@Size(max = 50, message = "{ad_title.too_long}")
	private String ad_title;

	private String id;
	@NotBlank(message = "{price.not_empty}")
	@Size(max = 8, message = "{price.too_long}")
	private String price;
	
	private ConditionsEntity vehicle_type;
	
	private BodyTypeEntity bodyTypeEntity;
	
	private VehicleModelsEntity vehicleModelsEntity;
	
	private FuelTypeEntity fuelTypeEntity;
	
	private String vehicleFeatures;
	
	@NotBlank(message = "{field.not_empty}")
	private String mileage;
	
	private GearTypeEntity gearTypeEntity;
	
	private EngineCapicityEntity engineCapicityEntity;
	
	private String main_image;
		
	private java.util.Date createdDate;
	
	// ignore in vehicle entity //
	private MultipartFile mainImageMultipartFile;

	// igonre in vehicle entity //
	private MultipartFile[] multipartFiles;

	private String originalCar;
	private String drivingLicence;
	private String paymentMethod;
	private String glass;

	@NotBlank(message = "{field.not_empty}")
	@Size(max = 500, message = "{field.too_long}")
	private String description;
	
	private UserEntity userEntity;
	
	private YearsEntity yearsEntities;
	
	private CityEntity cityEntity;

	private boolean isFeagtured;
	private boolean isMostPopular;

	private boolean isDeleted;

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public boolean isFeagtured() {
		return isFeagtured;
	}

	public void setFeagtured(boolean feagtured) {
		isFeagtured = feagtured;
	}

	public boolean isMostPopular() {
		return isMostPopular;
	}

	public void setMostPopular(boolean mostPopular) {
		isMostPopular = mostPopular;
	}

	public String getOriginalCar() {
		return originalCar;
	}

	public void setOriginalCar(String originalCar) {
		this.originalCar = originalCar;
	}

	public String getDrivingLicence() {
		return drivingLicence;
	}

	public void setDrivingLicence(String drivingLicence) {
		this.drivingLicence = drivingLicence;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getGlass() {
		return glass;
	}

	public void setGlass(String glass) {
		this.glass = glass;
	}

	public MultipartFile[] getMultipartFiles() {
		return multipartFiles;
	}


	public void setMultipartFiles(MultipartFile[] multipartFiles) {
		this.multipartFiles = multipartFiles;
	}


	public MultipartFile getMainImageMultipartFile() {
		return mainImageMultipartFile;
	}


	public void setMainImageMultipartFile(MultipartFile mainImageMultipartFile) {
		this.mainImageMultipartFile = mainImageMultipartFile;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "SubmitVehicleRequest{" +
				"ad_title='" + ad_title + '\'' +
				", price='" + price + '\'' +
				", vehicle_type=" + vehicle_type +
				", bodyTypeEntity=" + bodyTypeEntity +
				", vehicleModelsEntity=" + vehicleModelsEntity +
				", fuelTypeEntity=" + fuelTypeEntity +
				", vehicleFeatures='" + vehicleFeatures + '\'' +
				", mileage='" + mileage + '\'' +
				", gearTypeEntity=" + gearTypeEntity +
				", engineCapicityEntity=" + engineCapicityEntity +
				", main_image='" + main_image + '\'' +
				", createdDate=" + createdDate +
				", mainImageMultipartFile=" + mainImageMultipartFile +
				", multipartFiles=" + Arrays.toString(multipartFiles) +
				", originalCar='" + originalCar + '\'' +
				", drivingLicence='" + drivingLicence + '\'' +
				", paymentMethod='" + paymentMethod + '\'' +
				", glass='" + glass + '\'' +
				", description='" + description + '\'' +
				", userEntity=" + userEntity +
				", yearsEntities=" + yearsEntities +
				", cityEntity=" + cityEntity +
				'}';
	}
}









