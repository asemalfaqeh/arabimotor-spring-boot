package com.af.arabimotors.controllers.web;

import com.af.arabimotors.entities.BloggerEntity;
import com.af.arabimotors.services.BloggerService;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;

    @RequestMapping(value = WebUrlsConstants.MAGAZINE, method = RequestMethod.GET)
    public ModelAndView allBloggersController(){
        List<BloggerEntity> bloggerEntities = bloggerService.findAll().stream().limit(5).collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("blog", bloggerEntities);
        modelAndView.setViewName(WebViewsConstants.MAGAZINE_VIEW);
        return modelAndView;
    }

}
