package com.af.arabimotors.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.entities.UserSocialEntity;

@Repository
public interface UserSocialRepository extends JpaRepository<UserSocialEntity, Long>{
    UserSocialEntity findByUserEntity(UserEntity userEntity);
    
}
