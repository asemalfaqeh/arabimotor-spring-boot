package com.af.arabimotors.controllers.admin;

import com.af.arabimotors.utils.WebViewsConstants;

import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.af.arabimotors.entities.CityEntity;
import com.af.arabimotors.entities.ConfirmationEmailEntity;
import com.af.arabimotors.entities.SellerTypeEntity;
import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.model.request.ResetPasswordRequest;
import com.af.arabimotors.model.request.UserRequest;
import com.af.arabimotors.services.CityService;
import com.af.arabimotors.services.ConfirmEmailService;
import com.af.arabimotors.services.ConfirmationUserEmailService;
import com.af.arabimotors.services.CustomUserDetailsService;
import com.af.arabimotors.services.SellerTypeService;
import com.af.arabimotors.utils.UserAuthenticationHelper;
import com.af.arabimotors.utils.WebUrlsConstants;


@RestController
public class AuthController {

	@Autowired
	private CustomUserDetailsService userService;

	@Autowired
	private SellerTypeService SellerTypeService;

	@Autowired
	private CityService cityService;

	@Autowired
	private ConfirmEmailService confirmMailService;

	@Autowired
	private ConfirmationUserEmailService confirmUserEmail;
	
	@Autowired
	private BCryptPasswordEncoder bcryptpassword;

	@RequestMapping(value = WebUrlsConstants.LOGIN, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("title", "تسجيل الدخول");
		if (isAuthenticated()) {
			modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
		} else {
			modelAndView.setViewName(WebViewsConstants.LOGIN_VIEW);
		}
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.SIGN_UP, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		if (isAuthenticated()) {
			modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
		} else {

			List<CityEntity> cityEntities = cityService.findlAllCities();
			List<SellerTypeEntity> sellerTypeEntities = SellerTypeService.findAllSellerTypeEntities();
			modelAndView.addObject("types", sellerTypeEntities);
			modelAndView.addObject("cities", cityEntities);
			modelAndView.setViewName(WebViewsConstants.REGISTER_VIEW);

		}
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.ADMIN_DASHBOARD, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WebViewsConstants.ADMIN_DASHBOARD);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.SIGN_UP, method = RequestMethod.POST)
	public ModelAndView createNewUser(@Validated UserRequest user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();

		List<CityEntity> cityEntities = cityService.findlAllCities();
		List<SellerTypeEntity> sellerTypeEntities = SellerTypeService.findAllSellerTypeEntities();
		modelAndView.addObject("types", sellerTypeEntities);
		modelAndView.addObject("cities", cityEntities);
		modelAndView.addObject("error", "");

		UserEntity userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			modelAndView.addObject("error", "عذرا المستخدم مسجل مسبقآ");
			System.err.println("ExistUser: " + userExists.toString());
		} else {

			if (bindingResult.hasErrors()) {
				modelAndView.addObject("error", "يرجى التاكد من الحقول المطلوبة");
			} else {

				if (!user.getPassword().equals(user.getConfirm_password()) || user.getPassword().equals("")
						|| user.getPassword() == null) {
					modelAndView.addObject("error", "يرجى تاكيد كلمة المرور");
				} else {

					UserEntity userEntity = new UserEntity();
					BeanUtils.copyProperties(user, userEntity);
					userEntity.setCreated_at(new Date() + "");
					userEntity.setUser_photo("thumb.png");

					System.err.println("UserEntity: " + userEntity.toString());

					userService.saveUser(userEntity);

					ConfirmationEmailEntity confirmationToken = new ConfirmationEmailEntity(userEntity);
					confirmUserEmail.save(confirmationToken);

					SimpleMailMessage mailMessage = new SimpleMailMessage();
					mailMessage.setTo(user.getEmail());
					mailMessage.setSubject("Complete Registration!");
					mailMessage.setFrom("support@arabimotors.com");
					mailMessage.setText("Welcome To ArabiMotors, To Confirm your Account, Please click here : "
							+ WebUrlsConstants.VERIFY_EMAIL_LINK + confirmationToken.getConfirmationToken());

					confirmMailService.sendMain(mailMessage);

					modelAndView.setViewName(WebViewsConstants.LOGIN_VIEW);

				}

			}
		}

		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.CONFIRM_ACCOUNT, method = { RequestMethod.GET })
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {

		ConfirmationEmailEntity token = confirmUserEmail.findByToken(confirmationToken);

		if (token != null) {

			UserEntity user = userService.findUserByEmail(token.getUserEntity().getEmail());
			user.setEmailVerified(true);
			user.setEmailVerifiedAt(new Date() + "");
			user.setId(user.getId());
			userService.saveUser(user);
			confirmUserEmail.delete(token);
			modelAndView.setViewName("redirect:" + WebUrlsConstants.WEB_HOME_PAGE);
			confirmUserEmail.delete(token); // delete token //
		} else {
			modelAndView.setViewName("redirect:" + WebViewsConstants.ERROR_PAGE);
		}

		return modelAndView;

	}

	@RequestMapping(value = WebUrlsConstants.RESEND_VERIFY_EMAIL, method = RequestMethod.GET)
	public ModelAndView resendVerifyEmail() {

		String token = "";
		ModelAndView modelAndView = new ModelAndView();
		UserEntity userEntity = userService.findUserByEmail(UserAuthenticationHelper.getUserName());
		ConfirmationEmailEntity confirmationEmailEntity = confirmUserEmail.findConfirmationEmailEntity(userEntity);
		ConfirmationEmailEntity confirmationEmail = new ConfirmationEmailEntity(userEntity);

		if (confirmationEmailEntity == null) {
			confirmUserEmail.save(confirmationEmail);
		} else {
			confirmationEmail.setId(confirmationEmailEntity.getId());
			confirmUserEmail.save(confirmationEmailEntity);
		}

		token = confirmationEmailEntity.getConfirmationToken();

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userEntity.getEmail());
		mailMessage.setSubject("Complete Registration!");
		mailMessage.setFrom("ahh1994store@gmail.com");
		mailMessage.setText("Welcome To ArabiMotors, To Confirm your Account, Please click here : "
				+ WebUrlsConstants.VERIFY_EMAIL_LINK + token);

		confirmMailService.sendMain(mailMessage);

		modelAndView.setViewName("redirect:" + WebUrlsConstants.ACCOUNT_DETAILS);

		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.FORGOT_PASSWORD, method = RequestMethod.POST)
	public ModelAndView postforgotPassword(@RequestParam("email") String email) {

		ModelAndView modelAndView = new ModelAndView();
		if (email.isBlank()) {
			modelAndView.addObject("error", "يرجى التاكد من البريد الالكتروني");
			modelAndView.setViewName(WebViewsConstants.FORGOT_PASSWORD_VIEW);
			return modelAndView;
		} else {

			UserEntity userEntity = userService.findUserByEmail(email);

			if (userEntity == null) {
				modelAndView.addObject("error", "البريد الالكتروني الذي تم ادخال غير موجود");
				modelAndView.setViewName(WebViewsConstants.FORGOT_PASSWORD_VIEW);
				return modelAndView;
			}

			ConfirmationEmailEntity confirmationEmailEntity = confirmUserEmail.findConfirmationEmailEntity(userEntity);
			ConfirmationEmailEntity confirmationEmail = new ConfirmationEmailEntity(userEntity);
			String token;

			if (confirmationEmailEntity != null) { // update token
				confirmationEmail.setId(confirmationEmailEntity.getId());
			}

			confirmUserEmail.save(confirmationEmail);
			token = confirmationEmail.getConfirmationToken();
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(userEntity.getEmail());
			mailMessage.setSubject("Reset Password");
			mailMessage.setFrom("ahh1994store@gmail.com");
			mailMessage.setText("Welcome To ArabiMotors, To Reset Password your Account, Please click here : " + WebUrlsConstants.RESET_PASSWORD_LINK + token);
			modelAndView.setViewName(WebViewsConstants.FORGOT_PASSWORD_VIEW);
			confirmMailService.sendMain(mailMessage);

		}

		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.RESET_PASSWORD_POST, method =  RequestMethod.GET )
	public ModelAndView resetPassword(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {

		ConfirmationEmailEntity token = confirmUserEmail.findByToken(confirmationToken);

		if (token != null) {
			UserEntity user = userService.findUserByEmail(token.getUserEntity().getEmail());
			System.err.println("Email: " + user.getEmail());
			modelAndView.addObject("token", confirmationToken);
			modelAndView.setViewName(WebViewsConstants.RESET_PASSWORD_VIEW);
		} else {
			modelAndView.setViewName("redirect:" + WebUrlsConstants.ERROR_PAGE);
		}

		return modelAndView;

	}

	@RequestMapping(value = WebUrlsConstants.RESET_PASSWORD_POST, method = { RequestMethod.POST })
	public ModelAndView postResetPassword(@Validated ResetPasswordRequest request, BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();
		ConfirmationEmailEntity token = confirmUserEmail.findByToken(request.getToken());
		System.err.println("Token: " + request.getToken());
		if (token != null) {

			UserEntity user = userService.findUserByEmail(token.getUserEntity().getEmail());
 
			System.err.println("UserResetPasswordRequest: " + request.toString());

				if (result.hasErrors()) {
					modelAndView.addObject("error", result.getAllErrors());
					modelAndView.setViewName(WebViewsConstants.RESET_PASSWORD_VIEW);

					return modelAndView;
				}

				if (!request.getNew_password().equals(request.getConfirm_password())) {
					result.addError(new ObjectError("error", "يرجى تاكيد كلمة المرور"));
					modelAndView.setViewName(WebViewsConstants.RESET_PASSWORD_VIEW);
                    return modelAndView;
				}

				user.setPassword(bcryptpassword.encode(request.getNew_password()));
                userService.updateCurrentUser(user);
                confirmUserEmail.delete(token);
				modelAndView.setViewName("redirect:" + WebUrlsConstants.LOGIN);

		} else {
			modelAndView.setViewName("redirect:" + WebUrlsConstants.ERROR_PAGE);
		}

		return modelAndView;

	}

	@RequestMapping(value = WebUrlsConstants.FORGOT_PASSWORD, method = RequestMethod.GET)
	public ModelAndView getforgotPassword() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WebViewsConstants.FORGOT_PASSWORD_VIEW);
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.ERROR_PAGE, method = RequestMethod.GET)
	public ModelAndView notFoundPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(WebViewsConstants.ERROR_PAGE);
		return modelAndView;
	}

	private boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			return false;
		}
		return authentication.isAuthenticated();
	}

}
