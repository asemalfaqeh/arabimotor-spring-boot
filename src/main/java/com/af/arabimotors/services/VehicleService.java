package com.af.arabimotors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.ContactSellerEntity;
import com.af.arabimotors.entities.VehiclesEntity;
import com.af.arabimotors.model.request.AdvancedSearchRequest;
import com.af.arabimotors.repositories.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	public void saveVehicle(VehiclesEntity entity) {
		vehicleRepository.save(entity);
	}

	public List<VehiclesEntity> findFirstFourVehicles() {
		return vehicleRepository.findFirstFourVehicles();
	}

	public Optional<VehiclesEntity> findVehicleById(String id) {
		return vehicleRepository.findById(Long.parseLong(id));
	}

	public List<VehiclesEntity> findAll() {
		return vehicleRepository.findAll();
	}

	public List<VehiclesEntity> findAllOrderByPriceASC() {
		return vehicleRepository.findAllOrderByPriceAsc();
	}

	public List<VehiclesEntity> findAllOrderByPriceDESC() {
		return vehicleRepository.findAllOrderByPriceDesc();
	}

	public List<VehiclesEntity> findAllOrderByCreatedDate() {
		return vehicleRepository.findAllOrderByCreatedAt();
	}

	public List<VehiclesEntity> findAllByConditionType(String conditionType) {
		return vehicleRepository.findAllByCondition(conditionType);
	}

	public List<VehiclesEntity> findAllByUserId(String userId) {
		return vehicleRepository.findAllByUserId(userId);
	}


	public List<VehiclesEntity> findAdvanceSearch(AdvancedSearchRequest advancedSearchRequest) {
		return vehicleRepository.findAdvancedSearch(Integer.parseInt(advancedSearchRequest.getPrice()),
				advancedSearchRequest.getModel(), advancedSearchRequest.getYear(),
				advancedSearchRequest.getConditionType());
	}
	
	public List<VehiclesEntity> findAllByBodyTypeService(String bodyType){
		return vehicleRepository.findByBodyTypeEntity(bodyType);
	}

	public List<VehiclesEntity> findAdvanceSearchPrice(String minPrice, String maxPrice,
			AdvancedSearchRequest advancedSearchRequest) {
		return vehicleRepository.findAdvancedSearchPrice(Integer.parseInt(minPrice), Integer.parseInt(maxPrice),
				advancedSearchRequest.getModel(), advancedSearchRequest.getYear(),
				advancedSearchRequest.getConditionType());
	}

	public List<VehiclesEntity> findAdvanceSearchFBG(AdvancedSearchRequest advancedSearchRequest) {
		return vehicleRepository.findAdvancedSearchFGB(advancedSearchRequest.getFuelType(),
				advancedSearchRequest.getBodyType(), advancedSearchRequest.getGearType());
	}

	public List<VehiclesEntity> findbyAdTitleService(String adTitle) {
		return vehicleRepository.findByAd_title(adTitle);
	}

	public List<VehiclesEntity> findAllByUserEntityService(String userId){
		return vehicleRepository.findAllByUserEntity(userId);
	}

	public void deleteUserVehicle(String id){
		vehicleRepository.deleteById(Long.parseLong(id));
	}

}
