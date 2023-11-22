package com.eshop.eshopserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaServer
public class EshopServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopServiceRegistryApplication.class, args);
	}

	/*@Bean
	public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
	    EurekaInstanceConfigBean eicb = new EurekaInstanceConfigBean(inetUtils);
	    return eicb;
	}*/
}
