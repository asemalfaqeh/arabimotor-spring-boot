package com.af.arabimotors.services;

import com.af.arabimotors.entities.RoleEntity;
import com.af.arabimotors.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleEntity> allRolesService(){
        return roleRepository.findAll();
    }

}
