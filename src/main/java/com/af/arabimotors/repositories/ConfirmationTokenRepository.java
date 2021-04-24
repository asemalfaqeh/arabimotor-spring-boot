package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.ConfirmationEmailEntity;
import com.af.arabimotors.entities.UserEntity;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationEmailEntity, Long>{

    ConfirmationEmailEntity findByConfirmationToken(String confirmToken);
	ConfirmationEmailEntity findByUserEntity(UserEntity userEntity);
	
}
