package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.PriceEntity;
import com.af.arabimotors.repositories.MaxPriceRepository;

@Service
public class MaxPricesService {
	
	@Autowired
	private MaxPriceRepository priceRepository;
	
	public List<PriceEntity> findAllPrices(){
		return priceRepository.findAll();
	}

}
