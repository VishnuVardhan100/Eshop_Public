package com.eshop.eshopapplication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.eshop.eshopservice.service.CustomerLoginService;

/**
 * Eshop security configuration class
 */

@Configuration
@EnableWebSecurity
public class EshopSecurityConfiguration {
	
	@Autowired
	private CustomerLoginService customerLoginService;
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(customerLoginService);
	    authProvider.setPasswordEncoder(getPasswordEncoder());
	    return authProvider;
	}

	/**
	 * Returns Bcrypt Encoder
	 * @return Password Encoder
	 */
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
		//return new BCryptPasswordEncoder();		
	}

	/**
	 * HTTP Authorization for different roles
	 * @param http
	 * @return Security filter chain
	 * @throws Exception
	 */
	@Bean
    public SecurityFilterChain httpRequestFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
        		authorizeHttpRequests -> authorizeHttpRequests.requestMatchers("/admin/**").hasRole("ADMIN")
            	.requestMatchers("/customers/**").hasAnyRole("ADMIN","CUSTOMER")
            	.requestMatchers("/signup/**").permitAll()
            	.requestMatchers("/").permitAll())
        .csrf((csrf) -> csrf.disable())
        .formLogin(Customizer.withDefaults());
        return http.build();
	}

}
