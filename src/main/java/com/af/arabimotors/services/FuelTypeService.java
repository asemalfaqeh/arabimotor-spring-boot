package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.FuelTypeEntity;
import com.af.arabimotors.repositories.FuelTypeRepository;

@Service
public class FuelTypeService {

	@Autowired
	private FuelTypeRepository fuelTypeRepository;
	
	public List<FuelTypeEntity> findAllFuelTypeEntities(){
		return fuelTypeRepository.findAll();
	}
	
}
