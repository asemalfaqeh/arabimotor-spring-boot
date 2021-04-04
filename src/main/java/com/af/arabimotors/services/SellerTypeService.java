package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.SellerTypeEntity;
import com.af.arabimotors.repositories.SellerTypeRepository;

@Service
public class SellerTypeService {

	@Autowired
	private SellerTypeRepository sellerTypeRepository;
	
	public List<SellerTypeEntity> findAllSellerTypeEntities(){
		return sellerTypeRepository.findAll();
	}
	
	
}
