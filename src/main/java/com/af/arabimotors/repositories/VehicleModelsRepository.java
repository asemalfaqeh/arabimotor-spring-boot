package com.af.arabimotors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.af.arabimotors.entities.VehicleModelsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleModelsRepository extends JpaRepository<VehicleModelsEntity, Long> {

	public List<VehicleModelsEntity> findAllByEnabled(boolean isEnabled);
	
}
