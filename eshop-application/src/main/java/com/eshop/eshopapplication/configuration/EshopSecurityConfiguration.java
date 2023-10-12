package com.eshop.eshopapplication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eshop.eshopapplication.authentication.JwtUtilOld;
import com.eshop.eshopapplication.filter.JwtRequestFilter;
import com.eshop.eshopservice.service.CustomerLoginService;

/**
 * Eshop security configuration class
 */

@Configuration
@EnableWebSecurity
public class EshopSecurityConfiguration {
	
	@Autowired
	private CustomerLoginService customerLoginService;
	
	@Autowired
	private JwtUtilOld jwtUtilOld;

	@Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    private AuthenticationEntryPoint authEntryPoint;

	@Bean
	public JwtRequestFilter authenticationJwtTokenFilter() {
		return new JwtRequestFilter();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).build();
	}

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
		return new BCryptPasswordEncoder();
	}

	/**
	 * HTTP Authorization for endpoints based on different roles
	 * @param http
	 * @return Security filter chain
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain httpRequestFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
        	.csrf((csrf) -> csrf.disable())
        	.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
        	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        	.authorizeHttpRequests(
        			authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers("/","/favicon.ico").permitAll()
            .requestMatchers(HttpMethod.POST,"/signin/**").permitAll()
            .requestMatchers(HttpMethod.POST,"/signup/**").permitAll()
        	.requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/customers/**").hasAnyRole("CUSTOMER","ADMIN")
        	.anyRequest().authenticated()
        	)
        	.formLogin(Customizer.withDefaults())
        	.logout(Customizer.withDefaults());
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
	}

}
