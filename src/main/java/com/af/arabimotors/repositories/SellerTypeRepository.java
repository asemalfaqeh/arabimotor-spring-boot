package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.SellerTypeEntity;

@Repository
public interface SellerTypeRepository extends JpaRepository<SellerTypeEntity, Long> {

}
