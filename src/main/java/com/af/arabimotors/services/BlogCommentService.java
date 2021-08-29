package com.af.arabimotors.services;

import com.af.arabimotors.entities.BlogCommentsEntity;
import com.af.arabimotors.repositories.BlogCommentsRepository;
import com.af.arabimotors.repositories.BloggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCommentService {

    @Autowired
    private BlogCommentsRepository blogCommentsRepository;

    public void save(BlogCommentsEntity commentsEntity){
        blogCommentsRepository.save(commentsEntity);
    }

    public List<BlogCommentsEntity> commentsBlogCount(String id){
       return blogCommentsRepository.findAllCommentsByID(id);
    }

}
