package com.af.arabimotors.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.VehiclesEntity;
import com.af.arabimotors.repositories.VehicleRepository;

@Service
public class VehicleService {
	
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	public void saveVehicle(VehiclesEntity entity) {
		vehicleRepository.save(entity);
	}

    public List<VehiclesEntity> findFirstFourVehicles(){
    	return vehicleRepository.findFirstFourVehicles();
    }
	
    public Optional<VehiclesEntity> findVehicleById(String id) {
    	return vehicleRepository.findById(Long.parseLong(id));
    }
    
    public List<VehiclesEntity> findAll(){
    	return vehicleRepository.findAll();
    }
    
    public List<VehiclesEntity> findAllOrderByPriceASC(){
    	return vehicleRepository.findAllOrderByPriceAsc();
    }
    
    public List<VehiclesEntity> findAllOrderByPriceDESC(){
    	return vehicleRepository.findAllOrderByPriceDesc();
    }
    
    public List<VehiclesEntity> findAllOrderByCreatedDate(){
    	return vehicleRepository.findAllOrderByCreatedAt();
    }
    
    public List<VehiclesEntity> findAllByConditionType(String conditionType){
    	return vehicleRepository.findAllByCondition(conditionType);
    }
    
    
}
