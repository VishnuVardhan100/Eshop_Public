package com.eshop.eshopstandardgateway.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Eshop security configuration class
 */

@Configuration
@EnableWebFluxSecurity
public class EshopSecurityConfiguration {
	
    private EshopReactiveAuthenticationManager eshopReactiveAuthenticationManager;
	
    private EshopServerSecurityContextRepository eshopServerSecurityContextRepository;

    /**
     * Parameterized Constructor
     * @param EshopReactiveAuthenticationManager eshopReactiveAuthenticationManager
     * @param EshopServerSecurityContextRepository eshopServerSecurityContextRepository
     */
    public EshopSecurityConfiguration(EshopReactiveAuthenticationManager eshopReactiveAuthenticationManager, 
    		EshopServerSecurityContextRepository eshopServerSecurityContextRepository) {
    	this.eshopReactiveAuthenticationManager = eshopReactiveAuthenticationManager;
    	this.eshopServerSecurityContextRepository = eshopServerSecurityContextRepository;
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
	 * @param ServerHttpSecurity serverHttpSecurity
	 * @return Security web filter chain
	 * @throws Exception exception
	 */
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) throws Exception {
		serverHttpSecurity
			.cors(Customizer.withDefaults())
        	.csrf((csrf) -> csrf.disable())
//        	.exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
//
//        	.exceptionHandling()
//        	.authenticationEntryPoint((swe, e) -> 
//            	Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
//        	.accessDeniedHandler((swe, e) -> 
//        		Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
//        	.and()
//       	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationManager(eshopReactiveAuthenticationManager)
            .securityContextRepository(eshopServerSecurityContextRepository)
        	.authorizeExchange(
        			exchange -> exchange
        	.pathMatchers("/admin/**").hasRole("ADMIN")
            .pathMatchers("/customers/**").hasAnyRole("CUSTOMER","ADMIN")
            .pathMatchers("/","/favicon.ico").permitAll()
            .pathMatchers(HttpMethod.POST,"/signin/**").permitAll()
            .pathMatchers(HttpMethod.POST,"/signup/**").permitAll()
        	.anyExchange()
        	.authenticated()
        	)
//        	.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        	.formLogin(Customizer.withDefaults())
        	.httpBasic(http -> http.disable())
        	.logout(Customizer.withDefaults());
        
        return serverHttpSecurity.build();
	}

}
