package com.af.arabimotors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.BannerEntity;

@Repository
public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
  
	public List<BannerEntity> findAllByEnabled(boolean isEnabled);
	
	
}
