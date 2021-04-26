package com.af.arabimotors.services;

import java.util.List;

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

    public List<VehiclesEntity> findFirstFoutVehicles(){
    	return vehicleRepository.findFirstFoutVehicles();
    }
	
    public List<VehiclesEntity> findAll(){
    	return vehicleRepository.findAll();
    }
    
}
