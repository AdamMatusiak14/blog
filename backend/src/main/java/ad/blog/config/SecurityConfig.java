package ad.blog.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ad.blog.security.JwtAuthFilter;
import ad.blog.security.JwtTokenProvider;



@Configuration
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private  UserDetailsService userDetailsService;
    private JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;

    }

 


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return  http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login").permitAll()
            .anyRequest().authenticated()
        )
       // .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
       .formLogin(form -> form.loginProcessingUrl("/login"))
        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

// Masz 404 na wejściu, prawdopodobnie wywala Cię na filtrach  w Spring Security