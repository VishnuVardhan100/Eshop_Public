package com.eshop.eshopuserservice;

import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.annotation.PostConstruct;

@EntityScan("com.eshop")
@ComponentScan(basePackages={"com.eshop"})
@EnableJpaRepositories("com.eshop.eshopuserservice.repository")
@SpringBootApplication
@EnableFeignClients
public class EshopUserServiceApplication {

	public static Logger logger  = LoggerFactory.getLogger(EshopUserServiceApplication.class);
	
	@PostConstruct
	void init() {
		logger.info("User Service has started ...");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(EshopUserServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean  
	public LocaleResolver localeResolver()  
	{  
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();  
		localeResolver.setDefaultLocale(Locale.US);  
		return localeResolver;  
	}
	
}
