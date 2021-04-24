package com.af.arabimotors.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.ConfirmationEmailEntity;
import com.af.arabimotors.repositories.ConfirmationTokenRepository;

@Service("ConfirmEmailService")
public class ConfirmEmailService {

	
	private JavaMailSender sender;
	

	@Autowired
	public ConfirmEmailService(JavaMailSender sender) {
		this.sender = sender;
	}

	@Async
	public void sendMain(SimpleMailMessage message) {
		sender.send(message);
	}

	
}
