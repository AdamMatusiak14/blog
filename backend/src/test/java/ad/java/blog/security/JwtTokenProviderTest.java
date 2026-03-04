package ad.java.blog.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import javax.crypto.SecretKey;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import ad.blog.security.JwtTokenProvider;
import ad.blog.service.CustomDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@ExtendWith(MockitoExtension.class)
public class JwtTokenProviderTest {

    @Mock
    CustomDetailsService userDetailsService; 


    JwtTokenProvider jwtTokenProvider;

    SecretKey key = Jwts.SIG.HS256.key().build();

  
    @Test
    void generateToken_ShouldReturnNotNullToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(userDetailsService, key); 
        String token = jwtTokenProvider.generateToken("testuser", "ROLE_USER");

        Claims claims = Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
        
        assertNotNull(token);
        assertTrue(claims.getSubject().equals("testuser"));
        assertTrue(claims.get("role").equals("ROLE_USER"));   
        assertTrue(claims.getExpiration().after(new Date()));

   
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(userDetailsService, key); 
        String token = jwtTokenProvider.generateToken("testuser", "ROLE_USER");
        boolean isValid = jwtTokenProvider.validateToken(token);
        assertTrue(isValid); 
    }

    @Test 
    void validateToken_ShouldReturnFalseForInvalidToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(userDetailsService, key); 
        String invalidToken = "invalid.token.here";
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        assertFalse(isValid);

}

@Test
    void extractUsername_ShouldReturnCorrectUsername() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(userDetailsService, key); 
        String token = jwtTokenProvider.generateToken("testuser", "ROLE_USER");
        String username = jwtTokenProvider.extractUsername(token);
        assertNotNull(username);
        assertTrue(username.equals("testuser"));
        assertEquals("testuser", username);
    }


}