package com.af.arabimotors.controllers.web;

import java.util.*;
import java.util.stream.Collectors;

import com.af.arabimotors.entities.*;
import com.af.arabimotors.model.request.ContactSellerRequest;
import com.af.arabimotors.model.request.ContactUsRequest;
import com.af.arabimotors.services.*;

import com.af.arabimotors.utils.ComparableVehiclePrices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;

import javax.servlet.http.HttpServletRequest;

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

	@Autowired
	private ConfirmEmailService confirmEmailService;
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ContactSellerService contactSellerService;

	@Autowired
	private BloggerService bloggerService;

	@Autowired
	private ContactUsService contactUsService;

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
		List<UserEntity> userEntityList = customUserDetailsService.findAll();
		List<BloggerEntity> bloggerEntities = bloggerService.findAll();

		int sizeUserList = userEntityList.size();
		for (int i = 0; i < sizeUserList; i ++){
			logger.info("UserEnt : " + userEntityList.get(i).getRoles().iterator().next().getRole());
			String userRole = userEntityList.get(i).getRoles().iterator().next().getRole();
			if (userRole.equals("USER")){
				userEntityList.get(i).setUser_photo(userEntityList.get(i).getPhotosImagePath(userEntityList.get(i).getId(), userEntityList.get(i).getUser_photo()));
			}else{
				userEntityList.remove(i);
				logger.error("ADMIN EQ");
			}
		}

		vehiclesEntities.sort(new ComparableVehiclePrices());

		for(VehiclesEntity vehiclesEntity : vehiclesEntities) {
			if (vehiclesEntity.isFeagtured()) {
				fEntities.add(vehiclesEntity);
				logger.info(vehiclesEntities + "");
			}
		}

		for(VehiclesEntity vehiclesEntity : vehiclesEntities){
			if (vehiclesEntity.isMostPopular()) {
				mostPopularVehiclesEntities.add(vehiclesEntity);
				logger.warn("Most Popular " + vehiclesEntities+"");
			}
		}

		// sort by max price vehicles when features vehicles size < 5
		if (fEntities.size() < 5){
			for (VehiclesEntity vehiclesEntity : vehiclesEntities){
				if (fEntities.size() < 5){
					fEntities.add(vehiclesEntity);
				}
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
		model.addObject("users", userEntityList);
		model.addObject("bloggers", bloggerEntities);
				
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

	@RequestMapping(value = WebUrlsConstants.PRIVACY_POLICY, method = RequestMethod.GET)
	public ModelAndView privacyPolicyController(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WebViewsConstants.PRIVACY_POLICY_VIEW);
		return modelAndView;
	}

	@RequestMapping(value = {WebUrlsConstants.USER_PROFILE,WebUrlsConstants.USER_PROFILE+"/{userId}"}, method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView userProfileController(@RequestParam(value = "id", required = false) String userId,
											  @Validated ContactSellerRequest contactSellerRequest,
											  BindingResult bindingResult){

		    logger.info("UserIdProfile: " + userId);

		    if (userId == null && contactSellerRequest.getUserEntity() != null) {
				if (contactSellerRequest.getUserEntity().getId() != null) {
					userId = contactSellerRequest.getUserEntity().getId().toString();
				}
			}

	     	ModelAndView modelAndView = new ModelAndView();
			List<VehiclesEntity> vehiclesEntities = vehicleService.findAllByUserId(userId);
			if (vehiclesEntities.size() > 0) {
				UserEntity user = vehiclesEntities.get(0).getUserEntity();
				String photo;
				if (user.getUser_photo() != null) {
					photo = user.getPhotosImagePath(user.getId(), user.getUser_photo());
					logger.info("UserProfilePhoto: " + photo);
				} else {
					photo = "images/user.png";
				}

				if (contactSellerRequest.getUserEntity() != null) {
					if (bindingResult.hasErrors()) {
						modelAndView.addObject("error", bindingResult.getAllErrors());
					} else {
						ContactSellerEntity contactSellerEntity = new ContactSellerEntity();
						contactSellerRequest.setCreateDate(new Date());
						BeanUtils.copyProperties(contactSellerRequest, contactSellerEntity);
						contactSellerService.saveContactSellerInfo(contactSellerEntity);
						logger.info(contactSellerRequest.getCreateDate() + "");
						logger.info("Success Save Contact Seller Info");

						confirmEmailService.sendEmail("new message", contactSellerEntity.getCustomerMsg(),
								contactSellerEntity.getCustomerEmail(), user.getEmail());
					}
				}

				modelAndView.addObject("photo", photo);
				modelAndView.addObject("user", user);
				modelAndView.addObject("vehicles", vehiclesEntities);
				modelAndView.setViewName(WebViewsConstants.USER_PROFILE_VIEW);
			} else {
				modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
			}

		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.FEATURED_USERS, method = RequestMethod.GET)
	public ModelAndView allFeaturedUserController(){

		ModelAndView modelAndView = new ModelAndView();

		List<UserEntity> userEntityList = customUserDetailsService.findAll();

		int sizeUserList = userEntityList.size();
		for (int i = 0; i < sizeUserList; i ++){
			String userRole = userEntityList.get(i).getRoles().iterator().next().getRole();
			if (userRole.equals("USER")){
				userEntityList.get(i).setUser_photo(userEntityList.get(i).getPhotosImagePath(userEntityList.get(i).getId(), userEntityList.get(i).getUser_photo()));
			}else{
				userEntityList.remove(i);
			}
		}

		logger.info("allFeaturedUserController: " + userEntityList.get(0).getUser_photo());
		modelAndView.addObject("f_u", userEntityList);
		modelAndView.setViewName(WebViewsConstants.FEATURED_USERS_VIEW);

		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.CONTACT_US, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView contactUsController(@Validated Optional<ContactUsRequest> contactUsRequest, BindingResult result){
		ModelAndView modelAndView = new ModelAndView();

		if (contactUsRequest.isPresent()) {
			if (contactUsRequest.get().getEmail() != null) {
				if (result.hasErrors()) {
					modelAndView.addObject("error", result.getAllErrors());
				} else {
					ContactUsEntity contactUsEntity = new ContactUsEntity();
					BeanUtils.copyProperties(contactUsRequest.get(), contactUsEntity);
					logger.info("ContactUs:" + contactUsRequest.toString());
					contactUsService.saveContactUs(contactUsEntity);
					modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
				}
			}
		}

		modelAndView.setViewName(WebViewsConstants.CONTACT_US_VIEW);
		return modelAndView;
	}
	
	@RequestMapping("/")
	public ModelAndView  GoTohomePage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
		return modelAndView;
	}

}









