package com.af.arabimotors.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.af.arabimotors.entities.ContactSellerEntity;
import com.af.arabimotors.repositories.ContactSellerRepository;

@Service
public class ContactSellerService {

	@Autowired
	private ContactSellerRepository contactSellerRepository;
	
	public void saveContactSellerInfo(ContactSellerEntity contactSellerEntity) {
		contactSellerRepository.save(contactSellerEntity);
	}
	
}
