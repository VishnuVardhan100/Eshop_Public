package com.eshop.eshopapplication;

import java.util.Locale;

import org.hibernate.cfg.Environment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@EntityScan("com.eshop")
@ComponentScan(basePackages={"com.eshop"})
@EnableJpaRepositories("com.eshop.eshoprepository")
@SpringBootApplication
public class EshopApplication {
	
	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(EshopApplication.class, args);
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
