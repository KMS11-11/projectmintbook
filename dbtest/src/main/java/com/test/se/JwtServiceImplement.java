package com.test.se;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

@Service("jwtService")
public class JwtServiceImplement implements JwtService{
	
	private String secretKey="1234567890abcdefg##1234567890abcdefg##1234567890abcdefg##1234567890abcdefg##";

	@Override
	public String getToken(String key, Object value) {
		
		Date expTime=new Date();
		expTime.setTime(expTime.getTime()+1000*60*30);
		byte[] secretByteKey=DatatypeConverter.parseBase64Binary(secretKey);
		Key sighKey=new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
		
		Map<String, Object> headerMap=new HashMap<>();
		headerMap.put("typ", "JWT");
		headerMap.put("alg", "HS256");
		Map<String, Object> map=new HashMap<>();
		map.put(key, value);
		JwtBuilder builder=Jwts.builder().setHeader(headerMap).setClaims(map).setExpiration(expTime).signWith(sighKey, SignatureAlgorithm.HS256);
		
		return builder.compact();
	}

	@Override
	public Claims getClaims(String token) {
		if(token != null && !"".equals(token)) {
			try {
				byte[] secretByteKey=DatatypeConverter.parseBase64Binary(secretKey);
				Key signKey=new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
				return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
			}
			catch(ExpiredJwtException e) {
				// expired
			}
			catch(JwtException e) {
				//not validated
			}
		}
		return null;
	}

	@Override
	public boolean isValid(String token) {
		return this.getClaims(token) != null;
	}

	@Override
	public int getId(String token) {
		Claims claims=this.getClaims(token);
		if(claims!=null) {
			return Integer.parseInt(claims.get("id").toString());
		}
		return 0;
	}
	
}
