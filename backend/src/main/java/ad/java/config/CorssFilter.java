package ad.java.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorssFilter {  // Dodałeś go do projektu i wszytko działa proawidłowo
       @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // Zezwalamy na frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Dozwolone metody
        config.setAllowedHeaders(List.of("*")); // Dozwolone nagłówki
        config.setAllowCredentials(true); // Pozwalamy na ciasteczka i sesje

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
