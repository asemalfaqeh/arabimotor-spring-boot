package com.af.arabimotors.controllers.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.af.arabimotors.entities.*;
import com.af.arabimotors.services.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;

@Controller
public class HomeController {

	@Autowired
	private BodyTypeService bodyTypeService;

	@Autowired
	private VehicleModelsService vehicleModelsService;
	
	@Autowired
	private YearsService yearsService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private MaxPricesService maxPriceService;
	
	@Autowired
	private VehicleService vehicleService;
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@RequestMapping("/home")
	public ModelAndView homePage() {
		
		ModelAndView model = new ModelAndView();
		ArrayList<VehiclesEntity> fEntities = new ArrayList<>();
		ArrayList<VehiclesEntity> mostPopularVehiclesEntities = new ArrayList<>();

		List<VehicleModelsEntity> vehicleModelsEntities = vehicleModelsService.findAll(true);	
		List<YearsEntity> yearsEntities = yearsService.findAllYears();
		List<PriceEntity> priceEntities = maxPriceService.findAllPrices();
		List<BodyTypeEntity> bodyTypeEntities = bodyTypeService.findAllBodyType();
		List<VehiclesEntity> vehiclesEntities = vehicleService.findAll();
		
		
		for(VehiclesEntity vehiclesEntity : vehiclesEntities) {
			if (vehiclesEntity.isFeagtured()) {
				fEntities.add(vehiclesEntity);
				logger.warn(vehiclesEntity.toString() + "");
			}
		}
		
		for(VehiclesEntity vehiclesEntity : vehiclesEntities){
			if (vehiclesEntity.isMostPopular()) {
				mostPopularVehiclesEntities.add(vehiclesEntity);
				logger.warn("Most Popular " + vehiclesEntities.toString()+"");
			}
		}
		
		// get new added vehicles and set limit four items  //
		Collections.reverse(vehicleModelsEntities);
		List<VehiclesEntity> firstFourVehiclesEntities = vehiclesEntities.stream().limit(4).collect(Collectors.toList());
		
		model.addObject("models", vehicleModelsEntities);
		model.addObject("years", yearsEntities);
		model.addObject("prices", priceEntities);
		model.addObject("body_types", bodyTypeEntities);
		model.addObject("vehicles_best_ranking", firstFourVehiclesEntities);
		model.addObject("feature_vehicles", fEntities);
		model.addObject("most_popular_vehicles", mostPopularVehiclesEntities);
				
		model.setViewName(WebViewsConstants.USER_HOME);
		
		return model;
	}

	@RequestMapping(value = WebUrlsConstants.ABOUT_US, method = RequestMethod.GET)
	public ModelAndView aboutUsController(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("photo","uploads/cover.jpeg");
		modelAndView.setViewName(WebViewsConstants.ABOUT_VIEW);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.USER_PROFILE, method = RequestMethod.GET)
	public ModelAndView userProfileController(@RequestParam("id") String userId){
		logger.info("UserIdProfile: " + userId);
		ModelAndView modelAndView = new ModelAndView();
		List<VehiclesEntity> vehiclesEntities = vehicleService.findAllByUserId(userId);
		if (vehiclesEntities.size() > 0) {
			UserEntity user = vehiclesEntities.get(0).getUserEntity();
			String photo;
			if (user.getUser_photo() != null){
				photo = user.getPhotosImagePath(user.getId(), user.getUser_photo());
				logger.info("UserProfilePhoto: " + photo);
			}else {
				photo = "images/user.png";
			}
			modelAndView.addObject("photo", photo);
			modelAndView.addObject("user",user);
			modelAndView.addObject("vehicles", vehiclesEntities);
			modelAndView.setViewName(WebViewsConstants.USER_PROFILE_VIEW);
		}else {
			modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
		}
		return modelAndView;
	}
	
	@RequestMapping("/")
	public ModelAndView  GoTohomePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
		return modelAndView;
	}

}









