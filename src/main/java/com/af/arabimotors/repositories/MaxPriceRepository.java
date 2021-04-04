package com.af.arabimotors.repositories;

import com.af.arabimotors.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaxPriceRepository extends JpaRepository<PriceEntity, Long> {
    
}
