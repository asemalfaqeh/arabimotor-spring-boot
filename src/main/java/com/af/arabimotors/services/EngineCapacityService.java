package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.EngineCapicityEntity;
import com.af.arabimotors.repositories.EngineCapicityRepository;

@Service
public class EngineCapacityService {

	@Autowired
	private EngineCapicityRepository engineCapicityRepository;
	
	public List<EngineCapicityEntity> findallCapicityEntities(){
		return engineCapicityRepository.findAll();
	}
	
}
