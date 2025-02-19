package ad.blog.controller;

import java.security.Security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.security.AuthRequest;
import ad.blog.security.AuthResponse;
import ad.blog.security.JwtTokenProvider;

@RestController
//@RequestMapping("/login")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
       // try { 
            System.out.println("Jestem kontrolerem authController");
           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
          SecurityContextHolder.getContext().setAuthentication(authentication);

          String token = jwtTokenProvider.generateToken(authentication.getPrincipal().getClass().getName());
         
            
            return ResponseEntity.ok(new AuthResponse(token));
        // } catch (Exception e) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        // }
    }
}
