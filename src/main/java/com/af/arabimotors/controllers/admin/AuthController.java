package com.af.arabimotors.controllers.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.af.arabimotors.entities.UserEntity;
import com.af.arabimotors.services.CustomUserDetailsService;
import com.af.arabimotors.utils.WebUrlsConstants;

@Controller
public class AuthController {

	@Autowired
	private CustomUserDetailsService userService;

	@RequestMapping(value = WebUrlsConstants.LOGIN, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/login");
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.SIGN_UP, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		UserEntity user = new UserEntity();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("admin/signup");
		return modelAndView;
	}
	
	@RequestMapping(value = WebUrlsConstants.ADMIN_DASHBOARD, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin/dashboard");
		return modelAndView;
	}

	@RequestMapping(value = WebUrlsConstants.SIGN_UP, method = RequestMethod.POST)
	public ModelAndView createNewUser(@Validated UserEntity user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		UserEntity userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the username provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/signup");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new UserEntity());
			modelAndView.setViewName("admin/login");

		}
		
		return modelAndView;
	}

}
