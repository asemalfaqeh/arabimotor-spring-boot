package com.af.arabimotors.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.af.arabimotors.entities.YearsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface YearsRepository extends JpaRepository<YearsEntity,Long>{
  public List<YearsEntity> findAll();
}
