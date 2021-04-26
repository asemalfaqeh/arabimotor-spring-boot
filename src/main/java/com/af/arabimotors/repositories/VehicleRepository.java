package com.af.arabimotors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.VehiclesEntity;

@Repository
public interface VehicleRepository extends JpaRepository<VehiclesEntity, Long>{

	@Query(nativeQuery = true, value="SELECT * FROM vehicles v ORDER BY v.id DESC LIMIT 4")
	List<VehiclesEntity> findFirstFoutVehicles();
	
     List<VehiclesEntity> findAll();
}
