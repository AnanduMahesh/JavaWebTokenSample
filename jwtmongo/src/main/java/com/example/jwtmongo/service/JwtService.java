package com.example.jwtmongo.service;

import com.example.jwtmongo.model.User;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
@Service
public class JwtService {
//    public static final String SECRET = "secret-key";
    private static final SecretKey SECRET = Keys.hmacShaKeyFor("Super-Secret-key123".getBytes());
    public String generateToken(User user){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+86400000))
                .signWith(SECRET, SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractUsername(String token){
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
