package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	public List<CityEntity> findlAllCities(){
		return cityRepository.findAll();
	}
	
}
