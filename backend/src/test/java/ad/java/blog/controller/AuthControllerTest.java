package ad.java.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import ad.blog.controller.AuthController;
import ad.blog.security.AuthRequest;
import ad.blog.security.AuthResponse;
import ad.blog.security.JwtTokenProvider;
import ad.blog.service.CustomDetailsService;
import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    CustomDetailsService userDetailsService;

    @InjectMocks
    AuthController authController;


    AuthRequest authRequest = new AuthRequest("testuser", "testpassword");

    UserDetails userDetails = org.springframework.security.core.userdetails.User
            .withUsername("testuser")
            .password("testpassword")
            .authorities("ROLE_USER")
            .build();

    @Test
    void testLogin_Success() {

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(auth);

        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        when(jwtTokenProvider.generateToken("testuser", "ROLE_USER")).thenReturn("token123");

        ResponseEntity<AuthResponse> response = authController.login(authRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token123", response.getBody().getToken());
        
        
    }


    @Test
    void testLogin_AuthNotExist() {

        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Bad credentials"));

        ResponseEntity<AuthResponse> response = authController.login(authRequest);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Authentication failed: Bad credentials", response.getBody().getToken());
        
       
    }

    @Test
    void testLogin_UserNotExist() {
        AuthRequest authRequest = new AuthRequest("nonexistentuser", "testpassword");
        
        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("User not exist"));

        ResponseEntity<AuthResponse> response = authController.login(authRequest);
        
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Authentication failed: User not exist", response.getBody().getToken());
       
    }

    


}