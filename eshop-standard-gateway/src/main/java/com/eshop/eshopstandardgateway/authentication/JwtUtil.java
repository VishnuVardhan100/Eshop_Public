package com.eshop.eshopstandardgateway.authentication;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.eshop.eshopstandardgateway.model.customer.login.CustomerLoginDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * Default JWT class
 */

@Component
public class JwtUtil {

	@Value("$(jwt.secret.key)")
	private String jwtSecretKey;
	
	@Value("$(jwt.cookie.name)")
	private String jwtCookieName;

	@Value("${jwt.expiration.time}")
	private int jwtExpirationTime;
	
	@SuppressWarnings("unused")
	private Key key;
	
	/**
	 * Create a JWT
	 * @param claims
	 * @param username
	 * @return JWT as String
	 */
	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
				.signWith(SignatureAlgorithm.HS256, jwtSecretKey)
				.compact();
	}

	/**
	 * Extract a claims object
	 * @param token
	 * @return Claims object
	 */
	public Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.setSigningKey(jwtSecretKey)
				.parseClaimsJws(token)
				.getBody();
	}
	
	/**
	 * Extract a single claim
	 * @param <T>
	 * @param token
	 * @param claimsResolver
	 * @return extracted claim
	 */
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	/**
	 * Extract username from JWT
	 * @param token
	 * @return username as string
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Extract expiration date from JWT
	 * @param token
	 * @return expiration date
	 */
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	/**
	 * Check if JWT expired
	 * @param token
	 * @return true if JWT expired else false
	 */
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	/**
	 * Generate a new JWT
	 * @param customerLoginDetails
	 * @return JWT as string
	 */
	public String generateToken(CustomerLoginDetails customerLoginDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", customerLoginDetails.getRoles());
		return createToken(claims, customerLoginDetails.getUsername());
	}

	/**
	 * Initialize the Key in hmacSha
	 */
	@PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }
	
	/*@SuppressWarnings("unused")
	private Key key() {
	   return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
	}*/

	/**
	 * Check if JWT expired
	 * @param token
	 * @return true if JWT expired else false
	 */
	public boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
	
	/**
	 * Validate the JWT
	 * @param token
	 * @param userDetails
	 * @return true if JWT is valid else false
	 */
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
