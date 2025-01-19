package com.sachith.SpringSecEx.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class JWTService {

    private String secretKey ="";

    public JWTService(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String usenName) {

        Map<String, Objects> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(usenName)
                .issuedAt(new Date((System.currentTimeMillis())))
                .expiration(new Date(System.currentTimeMillis() +60*60*30))
                .and()
                .signWith(geyKey())
                .compact();
    }

    private Key geyKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
