package com.af.arabimotors.services;

import com.af.arabimotors.entities.ContactUsEntity;
import com.af.arabimotors.repositories.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactUsService {

    @Autowired
    private ContactUsRepository contactUsRepository;

    public void saveContactUs(ContactUsEntity contactUsEntity){
        contactUsRepository.save(contactUsEntity);
    }

}
