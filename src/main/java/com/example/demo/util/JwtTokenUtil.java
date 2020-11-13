package com.example.demo.util;

import java.util.Base64;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtTokenUtil {
	
	private static String secret = "ThisASecretLongEnoughToBeUsedInThisCaseWithJJWT";
	
	static {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}

	public static String createToken(String email) {
		String token = null;
	
		Claims claims = Jwts.claims().setSubject(email);
		SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));

        token = Jwts.builder()
        			.setClaims(claims)
        			.signWith(secretKey)
        			.compact();
		
		return token;
	}
	
}
