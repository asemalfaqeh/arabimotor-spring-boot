package com.af.arabimotors.controllers.user;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.af.arabimotors.entities.BodyTypeEntity;
import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.entities.ConditionsEntity;
import com.af.arabimotors.entities.ContactSellerEntity;
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
import com.af.arabimotors.services.ContactSellerService;
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

@RestController
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
	
	@Autowired
	private ContactSellerService contactSellerService;

	
	@RequestMapping(value = WebUrlsConstants.SUBMIT_VEHICLE, method = RequestMethod.GET)
	public ModelAndView getSubmitVehicle() {

		String email = UserAuthenticationHelper.getUserName();

		ModelAndView modelAndView = new ModelAndView();

		if (email.equals("anonymousUser")) {
			modelAndView.setViewName("redirect:" + WebUrlsConstants.LOGIN);
		} else {
			updateVehicleModelAndViewObjects(modelAndView, email);
			modelAndView.setViewName(WebViewsConstants.SUBMIT_VEHICL_VIEW);
		}

		return modelAndView;
		
	}

	@RequestMapping(value = WebUrlsConstants.SUBMIT_VEHICLE, method = RequestMethod.POST)
	public ModelAndView postNewVehicle(@Validated SubmitVehicleRequest submitVehicleRequest, BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView();

		String email = UserAuthenticationHelper.getUserName();
		UserEntity userEntity = customUserDetailsService.findUserByEmail(email);

		if (submitVehicleRequest.getId() != null) {
			if (customUserDetailsService.findUserByEmail(email).getEmail().equals("anonymousUser")) {
				modelAndView.setViewName("redirect:" + WebUrlsConstants.LOGIN);
			} else {
				Optional<VehiclesEntity> vehiclesEntity = vehicleService.findVehicleById(submitVehicleRequest.getId());
				if (vehiclesEntity.isPresent()){
					if (!vehiclesEntity.get().getUserEntity().getEmail().equals(email)){
						submitVehicleRequest.setId(null);
					}else {
						submitVehicleRequest.setFeagtured(vehiclesEntity.get().isFeagtured());
						submitVehicleRequest.setMostPopular(vehiclesEntity.get().isMostPopular());
						submitVehicleRequest.setMain_image(vehiclesEntity.get().getMain_image());
					}
				}
			}
		}

		if (bindingResult.hasErrors()) {
			modelAndView.addObject("error", bindingResult.getAllErrors());
			modelAndView.setViewName(WebViewsConstants.SUBMIT_VEHICL_VIEW);
			updateVehicleModelAndViewObjects(modelAndView, email);
			System.err.println("SubmitVehicleRequest:" + submitVehicleRequest.toString());
			return modelAndView;
		}

		if (submitVehicleRequest.getMainImageMultipartFile() != null) {
			try {
				// get original file name //
				Path absouletPath = Paths.get(".");
				String uploadDir = absouletPath + "/src/main/resources/static/uploads/";
				// set upload image path //
				FileUploadUtil.saveFile(uploadDir, submitVehicleRequest.getMainImageMultipartFile().getOriginalFilename(),
						submitVehicleRequest.getMainImageMultipartFile());
				// save gallery images //
				for (MultipartFile multipartFile : submitVehicleRequest.getMultipartFiles()) {
					System.err.println(multipartFile.getOriginalFilename());
					FileUploadUtil.saveFile(uploadDir, multipartFile.getOriginalFilename(), multipartFile);
				}

				submitVehicleRequest.setMain_image("uploads/" + submitVehicleRequest.getMainImageMultipartFile().getOriginalFilename());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Error: " + e.getMessage());
			}
		}

		VehiclesEntity vehiclesEntity = new VehiclesEntity();
		submitVehicleRequest.setUserEntity(userEntity); // user details
		submitVehicleRequest.setCreatedDate(new Date()); // created at date
		submitVehicleRequest.setPrice(submitVehicleRequest.getPrice());
		BeanUtils.copyProperties(submitVehicleRequest, vehiclesEntity);

		List<VehicleImagesEntity> vehicleImagesEntities = new ArrayList<>();

		if (submitVehicleRequest.getMultipartFiles() != null) {
			if (submitVehicleRequest.getMultipartFiles().length > 0) {
				for (MultipartFile multipartFile : submitVehicleRequest.getMultipartFiles()) {
					VehicleImagesEntity vehicleImagesRequest = new VehicleImagesEntity();
					vehicleImagesRequest.setImage("uploads/" + multipartFile.getOriginalFilename());
					vehicleImagesRequest.setVehiclesEntity(vehiclesEntity);
					vehicleImagesEntities.add(vehicleImagesRequest);
				}
			}
		}

		vehiclesEntity.setVehicleImagesEntity(vehicleImagesEntities);
		vehiclesEntity.setViews(0);
		if (submitVehicleRequest.getId() != null){
			vehiclesEntity.setId(Long.valueOf(submitVehicleRequest.getId()));
		}
		vehicleService.saveVehicle(vehiclesEntity);

		modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
		System.err.println("VehicleEntity: " + vehiclesEntity.toString());
		return modelAndView;

	}
	
	@RequestMapping(value = WebUrlsConstants.MESSAGES, method = RequestMethod.GET)
	public ModelAndView messagesController() {
		ModelAndView modelAndView = new ModelAndView();
		List<ContactSellerEntity> contactSellerEntities = contactSellerService.getContactSellerEntitiesService();
		Collections.reverse(contactSellerEntities);
		modelAndView.addObject("messages", contactSellerEntities);
		modelAndView.setViewName(WebViewsConstants.MESSAGES_VIEW);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.MY_VEHICLES, method = RequestMethod.GET)
	public ModelAndView allUserVehicles(){
		String email = UserAuthenticationHelper.getUserName();
		UserEntity userEntity = customUserDetailsService.findUserByEmail(email);
		List<VehiclesEntity> vehiclesEntities = vehicleService.findAllByUserEntityService(userEntity.getId()+"");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("vehicles", vehiclesEntities);
		modelAndView.setViewName(WebViewsConstants.MY_CARS);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.DELETE_VEHICLE+"/{id}", method = RequestMethod.GET)
	public ModelAndView deleteVehicle(@PathVariable("id") String id){
		ModelAndView modelAndView = new ModelAndView();
		String email = UserAuthenticationHelper.getUserName();
		UserEntity userEntity = customUserDetailsService.findUserByEmail(email);

		Optional<VehiclesEntity> vehiclesEntity = vehicleService.findVehicleById(id);
		if (vehiclesEntity.isPresent()) {
			if (vehiclesEntity.get().getUserEntity().getId().equals(userEntity.getId())) {
				vehiclesEntity.get().setDeleted(true);
				vehicleService.saveVehicle(vehiclesEntity.get());
			}
		}

		modelAndView.setViewName("redirect:" + WebUrlsConstants.MY_VEHICLES);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.EDIT_VEHICLE+"/{id}", method = RequestMethod.GET)
	public ModelAndView editVehicle(@PathVariable("id") String id){

		ModelAndView modelAndView = new ModelAndView();

		if (id != null){
			String email = UserAuthenticationHelper.getUserName();
			if (customUserDetailsService.findUserByEmail(email).getEmail().equals("anonymousUser")) {
				modelAndView.setViewName("redirect:" + WebUrlsConstants.LOGIN);
			} else {
				Optional<VehiclesEntity> vehiclesEntity = vehicleService.findVehicleById(id);
				if (vehiclesEntity.isPresent()) {
					if (vehiclesEntity.get().getUserEntity().getEmail().equals(email)) {
						vehiclesEntity.get().setId(Long.parseLong(id));
						modelAndView.addObject("vehicle", vehiclesEntity.get());
					}
				}
				updateVehicleModelAndViewObjects(modelAndView, email);
				modelAndView.setViewName(WebViewsConstants.EDIT_VEHICLE_VIEW);
			}
		}

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
		String userProfile = userEntity.getUser_photo();

		System.out.println("UserProfile: " + userProfile);
		if (userProfile.equals("thumb.png")){
			userEntity.setUser_photo("uploads/thumb.png");
			userProfile = userEntity.getUser_photo();
		}else{
			userProfile = userEntity.getPhotosImagePath(userEntity.getId(), userEntity.getUser_photo());
		}

		modelAndView.addObject("photo", userProfile);
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
