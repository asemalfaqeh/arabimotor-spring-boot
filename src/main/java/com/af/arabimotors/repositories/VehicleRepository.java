package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.VehiclesEntity;

@Repository
public interface VehicleRepository extends JpaRepository<VehiclesEntity, Long>{

}
