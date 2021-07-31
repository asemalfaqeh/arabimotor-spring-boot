package com.af.arabimotors.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.af.arabimotors.entities.VehicleImagesEntity;
import com.af.arabimotors.entities.VehiclesEntity;
import com.af.arabimotors.services.VehicleService;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;

@Controller
public class VehiclesController {

	@Autowired
	private VehicleService vehicleService;

	private Logger logger = LoggerFactory.getLogger(VehiclesController.class);

	@RequestMapping(value = WebUrlsConstants.VEHICLE_DETAILS + "/{id}", method = RequestMethod.GET)
	public ModelAndView getVehicleDetails(@PathVariable String id) {
		ModelAndView modelAndView = new ModelAndView();

		Optional<VehiclesEntity> vehiclesEntity = vehicleService.findVehicleById(id);
		List<String> images = new ArrayList<>();
		List<String> featureList = new ArrayList<>();
		Logger logger = LoggerFactory.getLogger(VehiclesController.class);

		logger.warn("");
		VehiclesEntity vehicle = new VehiclesEntity();
		String mainVehicleImageString = "";

		if (vehiclesEntity.isPresent()) {

			vehicle = vehiclesEntity.get();

			System.err.println("Vehicle: " + vehicle.toString());

			for (VehicleImagesEntity vehicleImagesEntity : vehicle.getVehicleImagesEntity()) {
				images.add("/" + vehicleImagesEntity.getImage());
			}

			if (images.size() != 0) {
				mainVehicleImageString = images.get(0);
			}

			if (vehicle.getVehicleModelsEntity() != null) {
				try {
					String featuresStr = vehicle.getVehicleFeatures();
					String[] featuresArr;

					if (featuresStr.contains("،")) {
						featuresArr = featuresStr.split("،");
					} else {
						featuresArr = featuresStr.split(",");
					}

					for (String featuers : featuresArr) {
						featureList.add(featuers);
					}
				} catch (Exception e) {
					// TODO: handle exception
					System.err.println(e.getMessage());
				}

			}

		}

		System.err.println(mainVehicleImageString);
		modelAndView.addObject("vehicle", vehicle);
		modelAndView.addObject("images", images);
		modelAndView.addObject("features", featureList);
		modelAndView.addObject("main_image", mainVehicleImageString);
		modelAndView.setViewName(WebViewsConstants.VEHICLE_DETAILS_VIEW);

		return modelAndView;

	}

	@RequestMapping(value = WebUrlsConstants.ALL_VEHICLES, method = RequestMethod.GET)
	public ModelAndView allVehicles(@RequestParam("sort") Optional<String> sortName, @RequestParam("condition_type") Optional<String> conditionType) {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName(WebViewsConstants.ALL_VEHICLES_VIEW);
		List<VehiclesEntity> vehiclesEntities = new ArrayList<>();
		// modelAndView.addObject("select_sort",1);
		if (sortName.isPresent()) {
			if (sortName.get().equals("1")) {
				vehiclesEntities = vehicleService.findAllOrderByPriceDESC();
				modelAndView.addObject("select_sort", 1);
			} else if (sortName.get().equals("2")) {
				vehiclesEntities = vehicleService.findAllOrderByPriceASC();
				modelAndView.addObject("select_sort", 2);
			} else if (sortName.get().equals("3")) {
				vehiclesEntities = vehicleService.findAllOrderByCreatedDate();
				modelAndView.addObject("select_sort", 3);
			}
		} else if(conditionType.isPresent()){
			logger.info("Condition Type: " + conditionType.get());

			if (conditionType.get().equals("new")) {
				vehiclesEntities = vehicleService.findAllByConditionType("1");
			}else if (conditionType.get().equals("used")) {
				vehiclesEntities = vehicleService.findAllByConditionType("2");
			}
			
			logger.info("Vehicles Select By Condition Type: " + vehiclesEntities.size());		
		
		}else {
			vehiclesEntities = vehicleService.findAll();
		}
		
	
		modelAndView.addObject("vehicles", vehiclesEntities);
		logger.debug("Vehicles Size: " + vehiclesEntities.size());
		
		return modelAndView;
		
	}

	/*
	 * @RequestMapping(value = WebUrlsConstants.VEHICLES_SORT, method =
	 * RequestMethod.GET) public ModelAndView sortVehicles(@RequestParam("sort")
	 * String sortName) { ModelAndView modelAndView = new ModelAndView();
	 * List<VehiclesEntity> vehiclesEntities = new ArrayList<>();
	 * 
	 * 
	 * 
	 * modelAndView.addObject("vehicles", vehiclesEntities);
	 * modelAndView.setViewName(WebViewsConstants.ALL_VEHICLES_VIEW); return
	 * modelAndView;
	 * 
	 * }
	 */

}
