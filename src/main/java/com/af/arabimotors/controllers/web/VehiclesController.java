package com.af.arabimotors.controllers.web;

import java.util.*;

import com.af.arabimotors.entities.*;
import com.af.arabimotors.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.af.arabimotors.model.request.AdvancedSearchRequest;
import com.af.arabimotors.model.request.ContactSellerRequest;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;

import javax.servlet.http.HttpSession;

@RestController
public class VehiclesController {

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private BodyTypeService bodyTypeService;

	@Autowired
	private YearsService yearsService;

	@Autowired
	private ConditionsService conditionsService;

	@Autowired
	private MaxPricesService maxPricesService;

	@Autowired
	private VehicleModelsService modelsService;

	@Autowired
	private GearTypeService gearTypeService;

	@Autowired
	private FuelTypeService fuelTypeService;

	@Autowired
	private ContactSellerService contactSellerService;

	@Autowired
	private UserSocialService userSocialService;

	@Autowired
	private ConfirmEmailService confirmEmailService;

	private final Logger logger = LoggerFactory.getLogger(VehiclesController.class);

	@RequestMapping(value = WebUrlsConstants.VEHICLE_DETAILS + "/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ModelAndView getVehicleDetails(@PathVariable String id,
			@Validated ContactSellerRequest contactSellerRequest, BindingResult bindingResult) {

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

			System.err.println("Vehicle: " + vehicle);

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

					featureList.addAll(Arrays.asList(featuresArr));

					UserSocialEntity userSocialEntity = userSocialService.findUserSocialEntity(vehicle.getUserEntity());
					modelAndView.addObject("social",userSocialEntity);

				} catch (Exception e) {
					// TODO: handle exception
					System.err.println(e.getMessage());
				}
			}
		}

		if (contactSellerRequest.getVehiclesEntity() != null) {
			if (bindingResult.hasErrors()) {
				modelAndView.addObject("error", bindingResult.getAllErrors());
			} else {
				ContactSellerEntity contactSellerEntity = new ContactSellerEntity();
				contactSellerRequest.setCreateDate(new Date());
				BeanUtils.copyProperties(contactSellerRequest, contactSellerEntity);
				contactSellerService.saveContactSellerInfo(contactSellerEntity);
				logger.info(contactSellerRequest.getCreateDate()+"");
				logger.info("Success Save Contact Seller Info");
				confirmEmailService.sendEmail(vehicle.getAd_title(), contactSellerEntity.getCustomerMsg()
				,contactSellerEntity.getCustomerEmail(), vehicle.getUserEntity().getEmail());
			}
		}

		modelAndView.addObject("vehicle", vehicle);
		modelAndView.addObject("images", images);
		modelAndView.addObject("features", featureList);
		modelAndView.addObject("main_image", mainVehicleImageString);
		modelAndView.setViewName(WebViewsConstants.VEHICLE_DETAILS_VIEW);

		return modelAndView;

	}

	@RequestMapping(value = WebUrlsConstants.ALL_VEHICLES, method = RequestMethod.GET)
	public ModelAndView allVehicles(@RequestParam("sort") Optional<String> sortName,
									@RequestParam("condition_type") Optional<String> conditionType, HttpSession httpSession) {

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName(WebViewsConstants.ALL_VEHICLES_VIEW);
		List<VehiclesEntity> vehiclesEntities = new ArrayList<>();
		String ct = null;
		if (httpSession.getAttribute("condition_type") != null) {
			ct = httpSession.getAttribute("condition_type").toString();
		}
		if (sortName.isPresent()) {
			if (sortName.get().equals("1")) {
				if (ct == null){
					vehiclesEntities = vehicleService.findAllOrderByPriceDESC();
				}else {
					vehiclesEntities = vehicleService.findCTAllOrderByPriceDESC(ct);
				}
				modelAndView.addObject("select_sort", 1);
			} else if (sortName.get().equals("2")) {
				if (ct == null){
					vehiclesEntities = vehicleService.findAllOrderByPriceASC();
				}else{
					vehiclesEntities = vehicleService.findCTOrderByPriceASC(ct);
				}
				modelAndView.addObject("select_sort", 2);
			} else if (sortName.get().equals("3")) {
				if (ct == null){
					vehiclesEntities = vehicleService.findAllOrderByCreatedDate();
				}else{
					vehiclesEntities = vehicleService.findCTAllOrderByCreatedDate(ct);
				}
				modelAndView.addObject("select_sort", 3);
			}
		} else if (conditionType.isPresent()) {
			if (conditionType.get().equals("new")) {
				httpSession.setAttribute("condition_type", "1");
				vehiclesEntities = vehicleService.findAllByConditionType("1");
			} else if (conditionType.get().equals("used")) {
				httpSession.setAttribute("condition_type", "2");
				vehiclesEntities = vehicleService.findAllByConditionType("2");
			}
		} else {
			vehiclesEntities = vehicleService.findAll();
		}
/*

 */

		modelAndView.addObject("vehicles", vehiclesEntities);
		logger.debug("Vehicles Size: " + vehiclesEntities.size());

		return modelAndView;

	}

	@RequestMapping(value = WebUrlsConstants.ALL_VEHICLES, method = RequestMethod.POST)
	public ModelAndView searchHomeVehiclesController(@Validated AdvancedSearchRequest advancedSearchRequest) {
		logger.info(advancedSearchRequest.toString());
		ModelAndView modelAndView = new ModelAndView();
		List<VehiclesEntity> lVehiclesEntities = vehicleService.findAdvanceSearch(advancedSearchRequest);
		modelAndView.addObject("vehicles", lVehiclesEntities);
		modelAndView.setViewName(WebViewsConstants.ALL_VEHICLES_VIEW);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.BODY_TYPE, method = RequestMethod.GET)
	public ModelAndView searchByBodyTypeController(@RequestParam("id") Optional<String> id, HttpSession httpSession) {
		ModelAndView modelAndView = new ModelAndView();
		httpSession.removeAttribute("condition_type");
		List<VehiclesEntity> vehicleEntities = vehicleService.findAllByBodyTypeService(id.get());
		modelAndView.addObject("vehicles", vehicleEntities);
		modelAndView.setViewName(WebViewsConstants.ALL_VEHICLES_VIEW);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.ADVANCED_SEARCH, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView advancedSearchController(@Validated AdvancedSearchRequest advancedSearchRequest, HttpSession httpSession) {

		ModelAndView modelAndView = new ModelAndView();
		logger.info("Model Session Attr Old: " + httpSession.getAttribute("model"));
		httpSession.setAttribute("model", advancedSearchRequest.getModel());
		logger.info("Model Session Attr " + httpSession.getAttribute("model"));
		logger.info("Condition Type: " + httpSession.getAttribute("condition_type"));

		List<VehiclesEntity> vehiclesEntities;
		if (advancedSearchRequest.getModel() != null && advancedSearchRequest.getYear() != null
				&& advancedSearchRequest.getConditionType() != null && advancedSearchRequest.getPrice() != null) {

			logger.info("Advanced Range Price: " + advancedSearchRequest.getPrice());
			String priceStr = advancedSearchRequest.getPrice().trim();
			String[] arrOfStr = priceStr.split("-");
			String minPrice;
			String maxPrice;
			maxPrice = arrOfStr[1].substring(1).trim();
			minPrice = arrOfStr[0].substring(1).trim();

			if (maxPrice.equals("$100000")){
				maxPrice = "100000";
			}
			if (maxPrice.contains("$")){
				maxPrice.replace("$","");
			}

			if (minPrice.contains("$")){
				minPrice.replace("$","");
			}

			logger.info("MaxPrice: " + maxPrice + " MinPrice: " + minPrice);

			vehiclesEntities = vehicleService.findAdvanceSearchPrice(minPrice, maxPrice, advancedSearchRequest);

		} else if (advancedSearchRequest.getBodyType() != null && advancedSearchRequest.getGearType() != null
				&& advancedSearchRequest.getFuelType() != null) {
			vehiclesEntities = vehicleService.findAdvanceSearchFBG(advancedSearchRequest);
		} else if (advancedSearchRequest.getAdTitle() != null) {
			vehiclesEntities = vehicleService.findbyAdTitleService(advancedSearchRequest.getAdTitle());
		} else {
			vehiclesEntities = vehicleService.findAll();
		}

		logger.info("All Data:" + advancedSearchRequest.toString());

		List<BodyTypeEntity> bodyTypeEntities = bodyTypeService.findAllBodyType();
		List<VehicleModelsEntity> vehicleModelsEntities = modelsService.findAll(true);
		List<FuelTypeEntity> fuelTypeEntities = fuelTypeService.findAllFuelTypeEntities();
		List<YearsEntity> yearsEntities = yearsService.findAllYears();
		List<ConditionsEntity> conditionsEntities = conditionsService.findAll();
		List<FuelTypeEntity> fTypeEntities = fuelTypeService.findAllFuelTypeEntities();
		List<BodyTypeEntity> bTypeEntities = bodyTypeService.findAllBodyType();
		List<GearTypeEntity> gearTypeEntities = gearTypeService.findGearTypeEntities();

		modelAndView.addObject("body_type", bodyTypeEntities);
		modelAndView.addObject("models", vehicleModelsEntities);
		modelAndView.addObject("fuel_type", fuelTypeEntities);
		modelAndView.addObject("years", yearsEntities);
		modelAndView.addObject("conditions", conditionsEntities);
		modelAndView.addObject("fuel_type", fTypeEntities);
		modelAndView.addObject("body_type", bTypeEntities);
		modelAndView.addObject("gear_type", gearTypeEntities);
		modelAndView.addObject("vehicles", vehiclesEntities);

		modelAndView.setViewName(WebViewsConstants.ADVANCED_SEARCH_VIEW);

		return modelAndView;

	}

}
