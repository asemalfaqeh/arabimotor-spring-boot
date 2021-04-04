package com.af.arabimotors.controllers.admin;


import com.af.arabimotors.utils.WebViewsConstants;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.entities.SellerTypeEntity;
import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.model.request.UserRequest;
import com.af.arabimotors.model.response.UserResponse;
import com.af.arabimotors.services.CityService;
import com.af.arabimotors.services.CustomUserDetailsService;
import com.af.arabimotors.services.SellerTypeService;
import com.af.arabimotors.utils.WebUrlsConstants;

@Controller
public class AuthController {

	@Autowired
	private CustomUserDetailsService userService;
	
	@Autowired
	private SellerTypeService SellerTypeService;
	
	@Autowired
	private CityService cityService;
	

	@RequestMapping(value = WebUrlsConstants.LOGIN, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "تسجيل الدخول");
		if(isAuthenticated()) {
			 modelAndView.setViewName("redirect:"+WebUrlsConstants.WEB_HOME_PAGE);
		}else {
		    modelAndView.setViewName(WebViewsConstants.LOGIN_VIEW);
		}
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.SIGN_UP, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		if(isAuthenticated()) {
			modelAndView.setViewName("redirect:"+WebUrlsConstants.WEB_HOME_PAGE);
		}else {
			
			List<CityEntity> cityEntities = cityService.findlAllCities();
			List<SellerTypeEntity> sellerTypeEntities = SellerTypeService.findAllSellerTypeEntities();
			
			modelAndView.addObject("types", sellerTypeEntities);
			modelAndView.addObject("cities", cityEntities);
	     	modelAndView.setViewName(WebViewsConstants.REGISTER_VIEW);
	     	
		}
		return modelAndView;
	}
	
	@RequestMapping(value = WebUrlsConstants.ADMIN_DASHBOARD, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WebViewsConstants.ADMIN_DASHBOARD);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.SIGN_UP, method = RequestMethod.POST)
	public ModelAndView createNewUser(@Validated UserRequest user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();

		List<CityEntity> cityEntities = cityService.findlAllCities();
		List<SellerTypeEntity> sellerTypeEntities = SellerTypeService.findAllSellerTypeEntities();
		modelAndView.addObject("types", sellerTypeEntities);
		modelAndView.addObject("cities", cityEntities);
		modelAndView.addObject("error","");
		
		UserEntity userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			modelAndView.addObject("error", "عذرا المستخدم مسجل مسبقآ");
			System.err.println("ExistUser: " + userExists.toString());
		}else {

			if (bindingResult.hasErrors()) {
				modelAndView.addObject("error","يرجى التاكد من الحقول المطلوبة");
			} else {
				
				if(!user.getPassword().equals(user.getConfirm_password())) {
					modelAndView.addObject("error","يرجى تاكيد كلمة المرور");
				}else {
				
				UserEntity userEntity = new UserEntity();
				BeanUtils.copyProperties(user, userEntity);
				userEntity.setCreated_at(new Date()+"");
				System.err.println("UserEntity: " + userEntity.toString());
				
				userService.saveUser(userEntity);
				modelAndView.setViewName(WebViewsConstants.LOGIN_VIEW);
				}

			}
		}
		
		return modelAndView;
	}

	
	private boolean isAuthenticated() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication == null || AnonymousAuthenticationToken.class.
	      isAssignableFrom(authentication.getClass())) {
	        return false;
	    }
	    return authentication.isAuthenticated();
	}

}
