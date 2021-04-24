package com.af.arabimotors.services;

import com.af.arabimotors.entities.BodyTypeEntity;
import com.af.arabimotors.repositories.BodyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeService {

    @Autowired
    private BodyTypeRepository bodyTypeRepository;

    public List<BodyTypeEntity> findAllBodyType(){
        return bodyTypeRepository.findAll();
    }
    

}
