package com.af.arabimotors.services;


import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.ConfirmationEmailEntity;
import com.af.arabimotors.repositories.ConfirmationTokenRepository;

@Service("ConfirmEmailService")
public class ConfirmEmailService {

	private final JavaMailSender sender;

	@Autowired
	public ConfirmEmailService(JavaMailSender sender) {
		this.sender = sender;
	}

	@Async
	public void sendMain(SimpleMailMessage message) {
		sender.send(message);
	}

	public void sendEmail(String title, String content, String emailFrom,String emailTo){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailTo);
		mailMessage.setSubject(title);
		mailMessage.setFrom(emailFrom);
		mailMessage.setText(content);
		sender.send(mailMessage);
	}
	
}
