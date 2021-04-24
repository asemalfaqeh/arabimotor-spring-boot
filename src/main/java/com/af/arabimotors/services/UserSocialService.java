package com.af.arabimotors.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.entities.UserSocialEntity;
import com.af.arabimotors.repositories.UserSocialRepository;

@Service
public class UserSocialService {

	@Autowired
	private UserSocialRepository userSocialRepository;
	
	public void saveOrUpdate(UserSocialEntity userSocialEntity) {
		userSocialRepository.save(userSocialEntity);
	}
	
	public UserSocialEntity findUserSocialEntity(UserEntity userEntity) {
		return userSocialRepository.findByUserEntity(userEntity);
	}
	
}
