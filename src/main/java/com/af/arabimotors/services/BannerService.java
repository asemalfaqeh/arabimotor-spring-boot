package com.af.arabimotors.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.BannerEntity;
import com.af.arabimotors.repositories.BannerRepository;

@Service
public class BannerService {

	@Autowired
	private BannerRepository bannerRepository;
	
	public List<BannerEntity> findlAllEnabled(boolean isEnabled){
		return bannerRepository.findAllByEnabled(isEnabled);
	}
	
}
