package com.af.arabimotors.controllers.user;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.entities.SellerTypeEntity;
import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.entities.UserSocialEntity;
import com.af.arabimotors.model.request.UserChangePasswordRequest;
import com.af.arabimotors.model.request.UserRequest;
import com.af.arabimotors.model.request.UserSocialRequest;
import com.af.arabimotors.model.response.UserResponse;
import com.af.arabimotors.services.CityService;
import com.af.arabimotors.services.CustomUserDetailsService;
import com.af.arabimotors.services.SellerTypeService;
import com.af.arabimotors.services.UserSocialService;
import com.af.arabimotors.utils.FileUploadUtil;
import com.af.arabimotors.utils.UserAuthenticationHelper;
import com.af.arabimotors.utils.WebUrlsConstants;
import com.af.arabimotors.utils.WebViewsConstants;

@Controller
public class AccountDetailsController {

	@Autowired
	private CustomUserDetailsService customUserSevice;

	@Autowired
	private SellerTypeService sellerTypeService;

	@Autowired
	private CityService cityService;
	
	@Autowired
	private UserSocialService userSocialService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	// get account details //
	@RequestMapping(value = WebUrlsConstants.ACCOUNT_DETAILS)
	public ModelAndView accountDetails() {
		ModelAndView modelAndView = new ModelAndView();

		List<CityEntity> cityEntities = cityService.findlAllCities();
		List<SellerTypeEntity> sellerTypeEntities = sellerTypeService.findAllSellerTypeEntities();
		
		System.out.println("Username: " + UserAuthenticationHelper.getUserName());
		UserEntity userEntity = customUserSevice.findUserByEmail(UserAuthenticationHelper.getUserName());
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userEntity, userResponse);
		
		
		modelAndView.addObject("photo", userEntity.getPhotosImagePath(userEntity.getId(), userEntity.getUser_photo()));
		modelAndView.addObject("isphoto",userEntity.getUser_photo());
		modelAndView.addObject("userdetails", userResponse);
		modelAndView.addObject("cities", cityEntities);
		modelAndView.addObject("types", sellerTypeEntities);
		
		System.out.println("UserResponse: " + userResponse.toString());
		modelAndView.setViewName(WebViewsConstants.ACCOUNT_DETAILS_VIEW);
		
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.UPDATE_USER_PROFILE)
	public ModelAndView updateUserProfile(@Validated UserRequest userRequest, BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();
	
		UserEntity userEntity = customUserSevice.findUserByEmail(UserAuthenticationHelper.getUserName());
	    
		List<CityEntity> cityEntities = cityService.findlAllCities();
		List<SellerTypeEntity> sellerTypeEntities = sellerTypeService.findAllSellerTypeEntities();
		
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(userEntity, userResponse);
		
		String photoString = userEntity.getPhotosImagePath(userEntity.getId(), userEntity.getUser_photo());
		System.out.println("UserPhoto: " + photoString);
		modelAndView.addObject("photo", photoString);
		modelAndView.addObject("isphoto",userEntity.getUser_photo());
		modelAndView.addObject("userdetails", userResponse);
		modelAndView.addObject("cities", cityEntities);
		modelAndView.addObject("types", sellerTypeEntities);
		
		userEntity.setAddress(userRequest.getAddress());
	    userEntity.setCity(userRequest.getCity());
	    userEntity.setSellerTypeEntity(userRequest.getSellerTypeEntity());
	    userEntity.setPhone(userRequest.getPhone());
	    userEntity.setFullname(userRequest.getFullname());
	    userEntity.setEmail(userRequest.getEmail());
	    
	    if(result.hasErrors()) {
            modelAndView.addObject("error", result.getAllErrors());    	
	    	modelAndView.setViewName(WebViewsConstants.ACCOUNT_DETAILS_VIEW);
	    	return modelAndView;
	    }
	    
	    customUserSevice.updateCurrentUser(userEntity);
	    
		modelAndView.setViewName("redirect:"+WebUrlsConstants.ACCOUNT_DETAILS);
		
		return modelAndView;

	}
	
	@RequestMapping(value = WebUrlsConstants.UPDATE_PHOTO_PROFILE, method = RequestMethod.POST)
	public ModelAndView updateProfilePhoto(@RequestParam("user_photo") MultipartFile multipartfile) {
		
		ModelAndView modelAndView = new ModelAndView();
		
		String fileName = null;
		if (multipartfile != null) {
			fileName = StringUtils.cleanPath(multipartfile.getOriginalFilename());
		}
		
		if (fileName != null) {
			
			UserEntity userEntity = customUserSevice.findUserByEmail(UserAuthenticationHelper.getUserName());
			System.err.println("UserName: " + UserAuthenticationHelper.getUserName());
			System.err.println("UserEntity: " + userEntity.toString());
		
			final long MB = (long) (Math.pow(1024, 2) * 2); // 2 MB Max Size Image /.
			
			try {
				
				if (multipartfile.getSize() > MB) {
					System.err.println("Image Big Size: " + multipartfile.getSize());
					modelAndView.setViewName("redirect:" + WebUrlsConstants.ACCOUNT_DETAILS);
					return modelAndView;
				}
				
				Path absouletPath = Paths.get(".");
				String uploadDir = absouletPath+"/src/main/resources/static/user-photos/" + userEntity.getId();
				System.err.println("Path: " + uploadDir);
				FileUploadUtil.saveFile(uploadDir, fileName, multipartfile);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				modelAndView.addObject("image_error", "يرجى محاولة رفع الصورة مرة اخرى");
			    e.printStackTrace();
			}
			
			userEntity.setUser_photo(fileName);
			customUserSevice.updateCurrentUser(userEntity);
			
		}
		
		modelAndView.setViewName("redirect:" + WebUrlsConstants.ACCOUNT_DETAILS);
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = WebUrlsConstants.CHANGE_PASSWORD, method = RequestMethod.GET)
	public ModelAndView changePassword() {
		ModelAndView modelAndView = new ModelAndView();
		UserEntity userEntity = customUserSevice.findUserByEmail(UserAuthenticationHelper.getUserName());
		String photoString = userEntity.getPhotosImagePath(userEntity.getId(), userEntity.getUser_photo());
        modelAndView.addObject("isphoto", userEntity.getUser_photo());
        modelAndView.addObject("photo", photoString);
		modelAndView.setViewName(WebViewsConstants.CHANGE_PASSWORD_VIEW);
		return modelAndView;
	}
	
	@RequestMapping(value = WebUrlsConstants.CHANGE_PASSWORD, method = RequestMethod.POST)
	public ModelAndView changePasswordPost(@Validated UserChangePasswordRequest userChangePasswordRequest, BindingResult bindingResult) {
		
		ModelAndView modelAndView = new ModelAndView();
		UserEntity userEntity = customUserSevice.findUserByEmail(UserAuthenticationHelper.getUserName());
		String photoString = userEntity.getPhotosImagePath(userEntity.getId(), userEntity.getUser_photo());
		modelAndView.addObject("isphoto", userEntity.getUser_photo());
	    modelAndView.addObject("photo", photoString);
		
	    if (bindingResult.hasErrors()) {
			modelAndView.addObject("error", bindingResult.getAllErrors());			
		}
	    
	  
	    if (!bcryptPasswordEncoder.matches(userChangePasswordRequest.getOld_password(), userEntity.getPassword())) {  
	    	String messageString = "كلمة المرور الحالية غير صحيحة";
	    	bindingResult.addError(new ObjectError("error", messageString));
	    	modelAndView.addObject("error", bindingResult.getAllErrors());
			modelAndView.setViewName(WebViewsConstants.CHANGE_PASSWORD_VIEW);
		}else if(!userChangePasswordRequest.getNew_password().equals(userChangePasswordRequest.getConfirm_password())){
			bindingResult.addError(new ObjectError("error","يرجى تاكيد كلمة المرور"));
	    	modelAndView.addObject("error", bindingResult.getAllErrors());
			modelAndView.setViewName(WebViewsConstants.CHANGE_PASSWORD_VIEW);
			return modelAndView;
		}else {
			System.err.println("NewPassword: " + userChangePasswordRequest.getNew_password());
	        userEntity.setPassword(bcryptPasswordEncoder.encode(userChangePasswordRequest.getNew_password()));
			System.err.println("EncodePassword: " + userEntity.getPassword());
			customUserSevice.updateCurrentUser(userEntity);
		}
	    
	  
		modelAndView.setViewName("redirect:"+WebUrlsConstants.ACCOUNT_DETAILS);
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = WebUrlsConstants.UPDATE_USER_PROFILE, method = RequestMethod.GET)
	public ModelAndView updateAccountGet() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:" + WebUrlsConstants.ACCOUNT_DETAILS);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.SOCIAL_MEDIA_LINKS, method = RequestMethod.GET)
	public ModelAndView userSocialMedia() {
		
		ModelAndView modelAndView = new ModelAndView();
	
		UserEntity userEntity = customUserSevice.findUserByEmail(UserAuthenticationHelper.getUserName());
		UserSocialEntity userSocialEntity = userSocialService.findUserSocialEntity(userEntity);
		if (userSocialEntity != null) {
		    modelAndView.addObject("user_social", userSocialEntity);
		}else {
		    modelAndView.addObject("user_social", new UserSocialEntity());
		}
		String photoString = userEntity.getPhotosImagePath(userEntity.getId(), userEntity.getUser_photo());
		modelAndView.addObject("isphoto", userEntity.getUser_photo());
	    modelAndView.addObject("photo", photoString);
        modelAndView.setViewName(WebViewsConstants.SOCIAL_USER_VIEW);			    
		return modelAndView;
		
	}
	
	
	@RequestMapping(value = WebUrlsConstants.SOCIAL_MEDIA_LINKS, method = RequestMethod.POST)
	public ModelAndView userSocialMediaPost(UserSocialRequest userSocialRequest) {
		
		ModelAndView modelAndView = new ModelAndView();
	
		UserEntity userEntity = customUserSevice.findUserByEmail(UserAuthenticationHelper.getUserName());
		UserSocialEntity socialEntity = userSocialService.findUserSocialEntity(userEntity);
		
		if (socialEntity != null) {
	        userSocialRequest.setId(socialEntity.getId());
		}
		
		UserSocialEntity userSocialEntity = new UserSocialEntity();

	    BeanUtils.copyProperties(userSocialRequest, userSocialEntity);
	    userSocialEntity.setUserEntity(userEntity);
	     
        modelAndView.setViewName("redirect:" + WebUrlsConstants.SOCIAL_MEDIA_LINKS);			    
		
        return modelAndView;
		
		
	}
	
}

