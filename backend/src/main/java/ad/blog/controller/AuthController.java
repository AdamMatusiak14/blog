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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.security.AuthRequest;
import ad.blog.security.AuthResponse;
import ad.blog.security.JwtTokenProvider;
import ad.blog.service.CustomDetailsService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, CustomDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
       
            System.out.println("Jestem kontrolerem authController");
            System.out.println("Login: "+ request.getUsername());
            System.out.println("Haslo: "+ request.getPassword());



            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
          SecurityContextHolder.getContext().setAuthentication(authentication);
          System.out.println("Username: " + authentication.getName());

          //String token = jwtTokenProvider.generateToken(authentication.getPrincipal().getClass().getName());
          String token = jwtTokenProvider.generateToken(request.getUsername());

          System.out.println("Token: " + token);
            
            return ResponseEntity.ok(new AuthResponse(token));
      
    }
}
