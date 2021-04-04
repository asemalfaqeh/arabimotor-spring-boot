package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

}
