package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.FuelTypeEntity;

@Repository
public interface FuelTypeRepository extends JpaRepository<FuelTypeEntity, Long> {

}
