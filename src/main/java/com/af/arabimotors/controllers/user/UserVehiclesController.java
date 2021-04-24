package com.af.arabimotors.controllers.user;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.af.arabimotors.entities.BodyTypeEntity;
import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.entities.ConditionsEntity;
import com.af.arabimotors.entities.EngineCapicityEntity;
import com.af.arabimotors.entities.FuelTypeEntity;
import com.af.arabimotors.entities.GearTypeEntity;
import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.entities.VehicleImagesEntity;
import com.af.arabimotors.entities.VehicleModelsEntity;
import com.af.arabimotors.entities.VehiclesEntity;
import com.af.arabimotors.entities.YearsEntity;
import com.af.arabimotors.model.request.SubmitVehicleRequest;
import com.af.arabimotors.services.BodyTypeService;
import com.af.arabimotors.services.CityService;
import com.af.arabimotors.services.ConditionsService;
import com.af.arabimotors.services.CustomUserDetailsService;
import com.af.arabimotors.services.EngineCapacityService;
import com.af.arabimotors.services.FuelTypeService;
import com.af.arabimotors.services.GearTypeService;
import com.af.arabimotors.services.VehicleModelsService;
import com.af.arabimotors.services.VehicleService;
import com.af.arabimotors.services.YearsService;
import com.af.arabimotors.utils.FileUploadUtil;
import com.af.arabimotors.utils.UserAuthenticationHelper;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;

@Controller
public class UserVehiclesController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private ConditionsService conditionsService;

	@Autowired
	private BodyTypeService bodyTypeService;

	@Autowired
	private FuelTypeService fuelTypeService;

	@Autowired
	private VehicleModelsService vehicleModelService;

	@Autowired
	private GearTypeService gearTypeService;

	@Autowired
	private EngineCapacityService engineCapacityService;

	@Autowired
	private YearsService yearService;

	@Autowired
	private CityService cityService;

	@Autowired
	private VehicleService vehicleService;

	@RequestMapping(value = WebUrlsConstants.SUBMIT_VEHICLE, method = RequestMethod.GET)
	public ModelAndView getSubmitVehicle() {

		String email = UserAuthenticationHelper.getUserName();

		ModelAndView modelAndView = new ModelAndView();

		if (email == "anonymousUser") {
			modelAndView.setViewName("redirect:" + WebUrlsConstants.LOGIN);
		} else {
			updateVehicleModelAndViewObjects(modelAndView, email);
			modelAndView.setViewName(WebViewsConstants.SUBMIT_VEHICL_VIEW);

		}

		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.SUBMIT_VEHICLE, method = RequestMethod.POST)
	public ModelAndView postNewVehicle(@Validated SubmitVehicleRequest submitVehicleRequest,
			BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView();

		String email = UserAuthenticationHelper.getUserName();
		UserEntity userEntity = customUserDetailsService.findUserByEmail(email);

		for (MultipartFile multipartFile : submitVehicleRequest.getMultipartFiles()) {
			System.err.println("ddd " + multipartFile.getOriginalFilename());
		}

		if (bindingResult.hasErrors()) {
			modelAndView.addObject("error", bindingResult.getAllErrors());
			modelAndView.setViewName(WebViewsConstants.SUBMIT_VEHICL_VIEW);
			updateVehicleModelAndViewObjects(modelAndView, email);
			System.err.println("SubmitVehicleRequest:" + submitVehicleRequest.toString());
			return modelAndView;
		}

		try {

			// get original file name //
			Path absouletPath = Paths.get(".");
			String uploadDir = absouletPath + "/src/main/resources/static/user-photos/" + userEntity.getId();
			// set upload image path //
			FileUploadUtil.saveFile(uploadDir, submitVehicleRequest.getMainImageMultipartFile().getOriginalFilename(),
					submitVehicleRequest.getMainImageMultipartFile());

			// save gallery images //
			for (MultipartFile multipartFile : submitVehicleRequest.getMultipartFiles()) {

				System.err.println(multipartFile.getOriginalFilename());
				FileUploadUtil.saveFile(uploadDir, multipartFile.getOriginalFilename(), multipartFile);

			}

			submitVehicleRequest.setMain_image(submitVehicleRequest.getMainImageMultipartFile().getOriginalFilename());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: " + e.getMessage());
		}

		VehiclesEntity vehiclesEntity = new VehiclesEntity();
		submitVehicleRequest.setUserEntity(userEntity); // user details
		submitVehicleRequest.setCreatedDate(new Date()); // created at date
		BeanUtils.copyProperties(submitVehicleRequest, vehiclesEntity);

		List<VehicleImagesEntity> vehicleImagesEntities = new ArrayList<>();

		for (MultipartFile multipartFile : submitVehicleRequest.getMultipartFiles()) {
			VehicleImagesEntity vehicleImagesRequest = new VehicleImagesEntity();
			vehicleImagesRequest.setImage(multipartFile.getOriginalFilename());
			vehicleImagesRequest.setVehiclesEntity(vehiclesEntity);
			vehicleImagesEntities.add(vehicleImagesRequest);
		}

		vehiclesEntity.setVehicleImagesEntity(vehicleImagesEntities);
		vehicleService.saveVehicle(vehiclesEntity);

		modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
		System.err.println("VehicleEntity: " + vehiclesEntity.toString());
		return modelAndView;

	}

	private void updateVehicleModelAndViewObjects(ModelAndView modelAndView, String email) {

		UserEntity userEntity = customUserDetailsService.findUserByEmail(email);
		List<ConditionsEntity> conditionsEntities = conditionsService.findAll();
		List<BodyTypeEntity> bodyTypeEntities = bodyTypeService.findAllBodyType();
		List<FuelTypeEntity> fuelTypeEntities = fuelTypeService.findAllFuelTypeEntities();
		List<VehicleModelsEntity> vehicleModelsEntities = vehicleModelService.findAll(true);
		List<GearTypeEntity> gearTypeEntities = gearTypeService.findGearTypeEntities();
		List<EngineCapicityEntity> engineCapicityEntities = engineCapacityService.findallCapicityEntities();
		List<YearsEntity> yearsEntities = yearService.findAllYears();
		List<CityEntity> cityEntities = cityService.findlAllCities();

		modelAndView.addObject("photo", userEntity.getPhotosImagePath(userEntity.getId(), userEntity.getUser_photo()));
		modelAndView.addObject("isphoto", userEntity.getUser_photo());
		modelAndView.addObject("vehicles_types", conditionsEntities);
		modelAndView.addObject("bodytypes", bodyTypeEntities);
		modelAndView.addObject("vehiclemodels", vehicleModelsEntities);
		modelAndView.addObject("fueltypes", fuelTypeEntities);
		modelAndView.addObject("geartypes", gearTypeEntities);
		modelAndView.addObject("capcities", engineCapicityEntities);
		modelAndView.addObject("years", yearsEntities);
		modelAndView.addObject("cities", cityEntities);

	}

}
