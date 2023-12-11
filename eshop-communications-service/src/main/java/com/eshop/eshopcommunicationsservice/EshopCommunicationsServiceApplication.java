package com.eshop.eshopcommunicationsservice;

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
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@EntityScan("com.eshop")
@ComponentScan(basePackages={"com.eshop"})
@SpringBootApplication
@EnableFeignClients
public class EshopCommunicationsServiceApplication {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(EshopCommunicationsServiceApplication.class, args);
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
	
	@KafkaListener(topics = "OrderNotificationTopic" + "_Order_Placed", groupId = "group1") 
	private void listen(String orderID) {
		logger.info("send Order notification executed for order: " + orderID);
	}
	
}
