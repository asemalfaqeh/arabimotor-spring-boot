package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.EngineCapicityEntity;

@Repository
public interface EngineCapicityRepository extends JpaRepository<EngineCapicityEntity, Long>{

}
