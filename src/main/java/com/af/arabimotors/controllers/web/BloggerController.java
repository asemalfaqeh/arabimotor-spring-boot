package com.af.arabimotors.controllers.web;

import com.af.arabimotors.entities.BlogCommentsEntity;
import com.af.arabimotors.entities.BloggerEntity;
import com.af.arabimotors.model.request.BlogCommentRequest;
import com.af.arabimotors.services.BlogCommentService;
import com.af.arabimotors.services.BloggerService;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BloggerController {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private BlogCommentService blogCommentService;

    private Logger logger = LoggerFactory.getLogger(BloggerController.class);


    @RequestMapping(value = WebUrlsConstants.MAGAZINE, method = RequestMethod.GET)
    public ModelAndView allBloggersController(@RequestParam("id") Optional<String> id){

        ModelAndView modelAndView = new ModelAndView();

        // if blog id not equal null
        if (id.isPresent()){

            Long aLong = Long.parseLong(id.get());
            Optional<BloggerEntity> bloggerEntity = bloggerService.findBloggerByID(aLong);
            List<BlogCommentsEntity> blogCommentsEntities = blogCommentService.commentsBlogCount(bloggerEntity.get().getId()+"");
            logger.info("Comments Size: " + blogCommentsEntities.size());
            List<BloggerEntity> bloggerEntities = bloggerService.findAll().stream().limit(5).collect(Collectors.toList());
            modelAndView.setViewName(WebViewsConstants.MAGAZINE_VIEW);
            bloggerEntity.ifPresent(entity -> modelAndView.addObject("blog", entity));
            modelAndView.addObject("comments", blogCommentsEntities);
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

    @RequestMapping(value = WebUrlsConstants.MAGAZINE, method = RequestMethod.POST)
    public ModelAndView postBlogComment(@Validated BlogCommentRequest blogCommentRequest, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()){
            modelAndView.addObject("error", bindingResult.getAllErrors());
            for (ObjectError error : bindingResult.getAllErrors()){
                logger.error("Save Blog Comment Error: " + error.getDefaultMessage());
                return null;
            }
        } else {
            BlogCommentsEntity blogCommentsEntity = new BlogCommentsEntity();
            BeanUtils.copyProperties(blogCommentRequest, blogCommentsEntity);
            blogCommentsEntity.setCreatedAt(new Date());
            logger.info("Blog Entity: " + blogCommentsEntity.toString());
            blogCommentService.save(blogCommentsEntity);
        }

        modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);

        return modelAndView;
    }

    @RequestMapping(value = WebUrlsConstants.SEARCH_MAGAZINE, method = RequestMethod.POST)
    public ModelAndView searchMagazineController(Optional<String> search){
        ModelAndView modelAndView = new ModelAndView();
        if (search.isPresent()) {
            List<BloggerEntity> bloggerEntities = bloggerService.findAllByName(search.get());
            modelAndView.addObject("blog", bloggerEntities);
            modelAndView.setViewName(WebViewsConstants.MAGAZINE_VIEW);
        }
        return modelAndView;
    }

}
