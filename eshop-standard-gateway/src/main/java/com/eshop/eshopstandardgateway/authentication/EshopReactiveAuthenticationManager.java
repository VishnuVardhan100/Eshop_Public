package com.eshop.eshopstandardgateway.authentication;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

/**
 * Reactive Authentication Manager implementation
 */

@Component
public class EshopReactiveAuthenticationManager implements ReactiveAuthenticationManager{

	private JwtUtil jwtUtil;

	/**
	 * Parameterized constructor
	 * @param jwtUtil
	 */
	public EshopReactiveAuthenticationManager(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username = jwtUtil.extractUsername(authToken);
        return Mono.just(jwtUtil.validateToken(authToken))
            .filter(valid -> valid)
            .switchIfEmpty(Mono.empty())
            .map(valid -> {
                Claims claims = jwtUtil.extractAllClaims(authToken);

                @SuppressWarnings("unchecked")
                List<String> roles = claims.get("role", List.class);

                return new UsernamePasswordAuthenticationToken(username, null, 
                		roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            	}
            );
    }

}
