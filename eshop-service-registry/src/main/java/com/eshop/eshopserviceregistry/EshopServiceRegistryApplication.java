package com.eshop.eshopserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EshopServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopServiceRegistryApplication.class, args);
	}

}
