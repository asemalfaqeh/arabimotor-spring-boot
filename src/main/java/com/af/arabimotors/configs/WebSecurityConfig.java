package com.af.arabimotors.configs;


import com.af.arabimotors.utils.WebUrlsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.af.arabimotors.services.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Bean
	public UserDetailsService jpaUserDetails() {
		return new CustomUserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserDetailsService userDetailsService = jpaUserDetails();
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(permitPathes()).permitAll()
				.antMatchers(userPathes()).hasAuthority("USER")
				.antMatchers(adminPathes()).hasAuthority("ADMIN").and()
				.csrf().disable().formLogin().successHandler(customizeAuthenticationSuccessHandler).loginPage("/login")
				.failureUrl("/login?error=true").usernameParameter("email").passwordParameter("password").and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").and()
				.exceptionHandling();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
	public String[] adminPathes() {
		return new String[]{
				WebUrlsConstants.ADMIN_ADD_CONDTITION,
				WebUrlsConstants.ADMIN_CONDITIONS,
				WebUrlsConstants.ADMIN_DASHBOARD,
				WebUrlsConstants.ADMIN_POST_NEW_CONDITION,
				WebUrlsConstants.ADMIN_EDIT_CONDITION, WebUrlsConstants.ADMIN_DELETE_CODNITION_BY_ID};
	}
	
	public String[] permitPathes() {
		return new String[] {
		  WebUrlsConstants.LOGIN,
		  WebUrlsConstants.SIGN_UP,
		  WebUrlsConstants.WEB_HOME_PAGE,
		  WebUrlsConstants.BASE_URL,
		  WebUrlsConstants.CONFIRM_ACCOUNT,
		  WebUrlsConstants.ERROR_PAGE
		};
	}
	
	// authenticated user paths // USER //
	public String[] userPathes() {
		return new String[] {
				  WebUrlsConstants.ACCOUNT_DETAILS,
				  WebUrlsConstants.UPDATE_USER_PROFILE,
				  WebUrlsConstants.UPDATE_PHOTO_PROFILE,
				  WebUrlsConstants.UPLOAD_PHOTO,
				  WebUrlsConstants.CHANGE_PASSWORD,
				  WebUrlsConstants.SOCIAL_MEDIA_LINKS,
				  WebUrlsConstants.RESEND_VERIFY_EMAIL,
				  WebUrlsConstants.SUBMIT_VEHICLE
				  
		};
	}

}
