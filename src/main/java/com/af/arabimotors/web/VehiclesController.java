package com.af.arabimotors.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.af.arabimotors.entities.BodyTypeEntity;
import com.af.arabimotors.entities.ConditionsEntity;
import com.af.arabimotors.entities.ContactSellerEntity;
import com.af.arabimotors.entities.FuelTypeEntity;
import com.af.arabimotors.entities.GearTypeEntity;
import com.af.arabimotors.entities.VehicleImagesEntity;
import com.af.arabimotors.entities.VehicleModelsEntity;
import com.af.arabimotors.entities.VehiclesEntity;
import com.af.arabimotors.entities.YearsEntity;
import com.af.arabimotors.model.request.AdvancedSearchRequest;
import com.af.arabimotors.model.request.ContactSellerRequest;
import com.af.arabimotors.services.BodyTypeService;
import com.af.arabimotors.services.ConditionsService;
import com.af.arabimotors.services.ContactSellerService;
import com.af.arabimotors.services.FuelTypeService;
import com.af.arabimotors.services.GearTypeService;
import com.af.arabimotors.services.MaxPricesService;
import com.af.arabimotors.services.VehicleModelsService;
import com.af.arabimotors.services.VehicleService;
import com.af.arabimotors.services.YearsService;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;

@Controller
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

	private Logger logger = LoggerFactory.getLogger(VehiclesController.class);

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

		if (contactSellerRequest.getVehiclesEntity() != null) {
			if (bindingResult.hasErrors()) {
				modelAndView.addObject("error", bindingResult.getAllErrors());
			} else {
				ContactSellerEntity contactSellerEntity = new ContactSellerEntity();
				contactSellerEntity.setCustomerEmail(contactSellerRequest.getCustomerEmail());
				contactSellerEntity.setCustomerMsg(contactSellerRequest.getCustomerMsg());
				contactSellerEntity.setCustomerName(contactSellerRequest.getCustomerName());
				contactSellerEntity.setCustomerPhone(contactSellerRequest.getCustomerPhone());
				contactSellerEntity.setVehiclesEntity(contactSellerRequest.getVehiclesEntity());
				contactSellerService.saveContactSellerInfo(contactSellerEntity);
				logger.info("Success Save Contact Seller Info");
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
			@RequestParam("condition_type") Optional<String> conditionType) {

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
		} else if (conditionType.isPresent()) {
			logger.info("Condition Type: " + conditionType.get());

			if (conditionType.get().equals("new")) {
				vehiclesEntities = vehicleService.findAllByConditionType("1");
			} else if (conditionType.get().equals("used")) {
				vehiclesEntities = vehicleService.findAllByConditionType("2");
			}

			logger.info("Vehicles Select By Condition Type: " + vehiclesEntities.size());

		} else {
			vehiclesEntities = vehicleService.findAll();
		}

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
	public ModelAndView searchByBodyTypeController(@RequestParam("id") Optional<String> id) {
		ModelAndView modelAndView = new ModelAndView();
		List<VehiclesEntity> vehicleEntities = vehicleService.findAllByBodyTypeService(id.get());
		modelAndView.addObject("vehicles", vehicleEntities);
		modelAndView.setViewName(WebViewsConstants.ALL_VEHICLES_VIEW);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.ADVANCED_SEARCH, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView advancedSearchController(@Validated AdvancedSearchRequest advancedSearchRequest) {

		ModelAndView modelAndView = new ModelAndView();
		List<VehiclesEntity> vehiclesEntities;
		if (advancedSearchRequest.getModel() != null && advancedSearchRequest.getYear() != null
				&& advancedSearchRequest.getConditionType() != null && advancedSearchRequest.getPrice() != null) {

			String priceStr = advancedSearchRequest.getPrice().toString();
			String[] arrOfStr = priceStr.split("-");
			String minPrice = "0";
			String maxPrice = "0";
			minPrice = arrOfStr[1].substring(1, arrOfStr[0].length());
			maxPrice = arrOfStr[0].substring(1, arrOfStr[1].length());
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
