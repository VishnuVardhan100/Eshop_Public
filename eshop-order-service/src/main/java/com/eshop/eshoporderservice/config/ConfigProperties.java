package com.eshop.eshoporderservice.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Class to retrieve environment variables
 */

@Configuration
@PropertySource(value = {"classpath:application.yml"})
public class ConfigProperties implements EnvironmentAware{

	private Environment env;
	
	public String getConfigValue(String configKey){
        return env.getProperty(configKey);
    }

	@Override
	public void setEnvironment(final Environment env) {
		this.env = env;
		
	}
	
}
