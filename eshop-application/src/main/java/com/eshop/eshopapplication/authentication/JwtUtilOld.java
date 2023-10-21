package com.eshop.eshopapplication.authentication;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * 
 */

@Component
public class JwtUtilOld {

	//@Value("$(jwt.secret.key)")
	private String jwtSecretKey = "helloEshopLetMeInYouBetterDoWhoLetTheDogsOutWHOWHOWHOYEAHH";
	@Value("$(jwt.cookie.name)")
	private String jwtCookieName;

	private int jwtExpirationTime = 19800000;
	
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
				.signWith(SignatureAlgorithm.HS256, jwtSecretKey).compact();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token).getBody();
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims,userDetails.getUsername());
	}
	
	@SuppressWarnings("unused")
	private Key key() {
	   return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
