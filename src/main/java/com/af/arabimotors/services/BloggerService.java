package com.af.arabimotors.services;

import com.af.arabimotors.entities.BloggerEntity;
import com.af.arabimotors.repositories.BloggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloggerService {

    @Autowired
    private BloggerRepository bloggerRepository;

    public List<BloggerEntity> findAll(){
        return bloggerRepository.findAll();
    }

    public Optional<BloggerEntity> findBloggerByID(Long id){
        return bloggerRepository.findById(id);
    }

    public List<BloggerEntity> findAllByName(String searchName){
        return bloggerRepository.findAllBySearchName(searchName);
    }

}
