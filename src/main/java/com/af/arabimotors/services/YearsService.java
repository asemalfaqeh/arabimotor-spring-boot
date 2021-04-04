package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.YearsEntity;
import com.af.arabimotors.repositories.YearsRepository;

@Service
public class YearsService {

	@Autowired
	private YearsRepository yearsRepository;
	
	
	// find all years //
	public List<YearsEntity> findAllYears(){
		return yearsRepository.findAll();
	}
	
	
	
}
