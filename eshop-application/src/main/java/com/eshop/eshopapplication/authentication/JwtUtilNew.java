package com.eshop.eshopapplication.authentication;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * JWT Utility class for JSON Web Tokens
 */

@Component
public class JwtUtilNew {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtilNew.class);
	
	@Value("$(jwt.secret.key)")
	private String jwtSecretKey;

	@Value("$(jwt.cookie.name)")
	private String jwtCookieName;

	@Value("$(jwt.expiration.time)")
	private String jwtExpirationTimeString;
	
	private int jwtExpirationTime = 19800000;
	
	/**
	 * Build JWT Cookie
	 * @return Response Cookie
	 */
	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookieName, null).build();
		return cookie;
	}
	
	/**
	 * Fetch JWT From Cookie
	 * @param request
	 * @return cookie
	 */
	public String getJwtFromCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
	    if (cookie != null) {
	    	return cookie.getValue();
	    } else {
	    	return null;
	    }
	}

	/**
	 * Get user name from token
	 * @param JWT token
	 * @return Username from JWT
	 */
	private String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	/**Get the base 64 decoded secret key
	 * @return decoded base 64 secret key
	 */
	private Key key() {
	    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
	}

	/**
	 * Generate Cookie with JWT Token
	 * @param userDetails
	 * @return Response Cookie
	 */
	public ResponseCookie generateJwtCookie(UserDetails userDetails) {
		//jwtExpirationTime = Integer.parseInt(jwtExpirationTimeString);
		String jwt = generateTokenFromUsername(userDetails.getUsername());
		ResponseCookie cookie = ResponseCookie.from(jwtCookieName, jwt).maxAge(jwtExpirationTime).httpOnly(true).build();
		return cookie;
	}
	
	/**
	 * Build a JSON Web Token
	 * @param username
	 * @return JSON Web Token
	 */
	public String generateTokenFromUsername(String username) {
		//jwtExpirationTime = Integer.parseInt(jwtExpirationTimeString);
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationTime))
				.signWith(key(), SignatureAlgorithm.HS256)
				.compact();
	  }	

	/**
	 * Check if JWT is valid
	 * @param authToken
	 * @return validation status
	 */
	public boolean validateJwtToken(String authToken) {

		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			return true;
		}
		catch (MalformedJwtException me) {
			logger.error("Invalid JWT token: {}", me.getMessage());
		}
		catch (ExpiredJwtException eje) {
			logger.error("JWT token is expired: {}", eje.getMessage());
		}
		catch (UnsupportedJwtException uje) {
			logger.error("JWT token is unsupported: {}", uje.getMessage());
		}
		catch (IllegalArgumentException iae) {
			logger.error("JWT claims string is empty: {}", iae.getMessage());
		}

		return false;
	}
	
}
