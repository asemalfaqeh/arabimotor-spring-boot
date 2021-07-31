package com.af.arabimotors.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.af.arabimotors.entities.*;
import com.af.arabimotors.services.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping("/")
	public ModelAndView  GoTohomePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
		return modelAndView;
	}

}









