package com.af.arabimotors.controllers.web;

import com.af.arabimotors.entities.BloggerEntity;
import com.af.arabimotors.services.BloggerService;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;

    @RequestMapping(value = WebUrlsConstants.MAGAZINE, method = RequestMethod.GET)
    public ModelAndView allBloggersController(@RequestParam("id") Optional<String> id){

        ModelAndView modelAndView = new ModelAndView();

        // if blog id not equal null
        if (id.isPresent()){
            Long aLong = Long.parseLong(id.get());
            Optional<BloggerEntity> bloggerEntity = bloggerService.findBloggerByID(aLong);
            List<BloggerEntity> bloggerEntities = bloggerService.findAll().stream().limit(5).collect(Collectors.toList());
            modelAndView.setViewName(WebViewsConstants.MAGAZINE_VIEW);
            bloggerEntity.ifPresent(entity -> modelAndView.addObject("blog", entity));
            modelAndView.addObject("blogs", bloggerEntities);
            modelAndView.setViewName(WebViewsConstants.BLOG_DETAILS);
        }else {
            // get all bloggers
            List<BloggerEntity> bloggerEntities = bloggerService.findAll().stream().limit(5).collect(Collectors.toList());
            modelAndView.addObject("blog", bloggerEntities);
            modelAndView.setViewName(WebViewsConstants.MAGAZINE_VIEW);
        }

        return modelAndView;

    }


}
