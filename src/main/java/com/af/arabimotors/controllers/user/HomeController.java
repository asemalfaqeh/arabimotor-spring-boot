package com.af.arabimotors.controllers.user;

import java.util.ArrayList;
import java.util.List;

import com.af.arabimotors.entities.*;
import com.af.arabimotors.services.*;

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
	

	@RequestMapping("/home")
	public ModelAndView homePage() {
		
		ModelAndView model = new ModelAndView();
		
		List<VehicleModelsEntity> vehicleModelsEntities = vehicleModelsService.findAll(true);	
		List<YearsEntity> yearsEntities = yearsService.findAllYears();
		List<PriceEntity> priceEntities = maxPriceService.findAllPrices();
		List<BodyTypeEntity> bodyTypeEntities = bodyTypeService.findAllBodyType();
		
		model.addObject("models", vehicleModelsEntities);
		model.addObject("years", yearsEntities);
		model.addObject("prices", priceEntities);
		model.addObject("body_types", bodyTypeEntities);
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
