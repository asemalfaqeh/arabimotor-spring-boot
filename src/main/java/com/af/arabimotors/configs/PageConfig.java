package com.af.arabimotors.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.af.arabimotors.utils.WebUrlsConstants;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class PageConfig implements WebMvcConfigurer {
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/login").setViewName("/login");
	    registry.addViewController("/home").setViewName("website/home");
	    registry.addViewController("/").setViewName("webstie/home");
	    
	}


	@Bean
	public ClassLoaderTemplateResolver yourTemplateResolver() {
		ClassLoaderTemplateResolver configurer = new ClassLoaderTemplateResolver();
		configurer.setPrefix("templates/");
		configurer.setSuffix(".html");
		configurer.setTemplateMode(TemplateMode.HTML);
		configurer.setCharacterEncoding("UTF-8");
		configurer.setOrder(0);  // this is important. This way spring //boot will listen to both places 0 and 1
		configurer.setCheckExistence(true);
	   return configurer;
	}

}
