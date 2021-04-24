package com.af.arabimotors.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.ConfirmationEmailEntity;
import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.repositories.ConfirmationTokenRepository;


@Service
public class ConfirmationUserEmailService {

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	
	public ConfirmationEmailEntity findByToken(String confirmToken) {
		return confirmationTokenRepository.findByConfirmationToken(confirmToken);
	}

	public ConfirmationEmailEntity findConfirmationEmailEntity(UserEntity userEntity) {
		return confirmationTokenRepository.findByUserEntity(userEntity);
	}
	
	public void save(ConfirmationEmailEntity confirmationEmailEntity) {
		confirmationTokenRepository.save(confirmationEmailEntity);
	}
	
	public void delete(ConfirmationEmailEntity confirmationEmailEntity) {
		confirmationTokenRepository.delete(confirmationEmailEntity);
	}
	
}
