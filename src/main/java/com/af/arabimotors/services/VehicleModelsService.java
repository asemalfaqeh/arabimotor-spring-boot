package com.af.arabimotors.services;

import java.util.List;

import com.af.arabimotors.repositories.VehicleModelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.VehicleModelsEntity;

@Service
public class VehicleModelsService {

	@Autowired
	private VehicleModelsRepository vehicleModelsRepository;

	public List<VehicleModelsEntity> findAll(boolean isEnabled){
		return vehicleModelsRepository.findAllByEnabled(isEnabled);
	}
	
}
