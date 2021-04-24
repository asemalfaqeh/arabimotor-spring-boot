package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.GearTypeEntity;
import com.af.arabimotors.repositories.GearTypeRepository;

@Service
public class GearTypeService {

	@Autowired
    private GearTypeRepository gearTypeRepository;
	
	public List<GearTypeEntity> findGearTypeEntities(){
		return gearTypeRepository.findAll();
	}
	
}
