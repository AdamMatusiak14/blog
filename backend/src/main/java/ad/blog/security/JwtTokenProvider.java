package ad.blog.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import ad.blog.service.CustomDetailsService;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {
    private SecretKey key = Jwts.SIG.HS256.key().build();
    private final long validityInMs = 3600000; // 1 godzina
    private final CustomDetailsService userDetailsService;

   
    public JwtTokenProvider(CustomDetailsService userDetailsService, SecretKey key) {
        this.userDetailsService = userDetailsService;
        this.key = key;
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
        .subject(username)
        .claim("role", role)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + validityInMs))
        .signWith(key, Jwts.SIG.HS256) // Trzeba jawnie podać algorytm
        .compact();

    }

    public boolean validateToken(String token) {
        try {
           
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            System.out.println("Token niepoprawny: " + e.getMessage());
            return false;
        }
    }

    public String extractUsername(String token) {
      return Jwts.parser()
       .verifyWith(key)
       .build()
       .parseSignedClaims(token)
       .getPayload()
       .getSubject();
               
    }
    
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getExpiration();
        return expiration.before(new Date());
    }
    
    public String extractRole(String token) {
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .get("role", String.class);
}
}




