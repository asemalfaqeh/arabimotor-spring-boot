package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.ContactSellerEntity;

@Repository
public interface ContactSellerRepository extends JpaRepository<ContactSellerEntity, Long>{

	
}
