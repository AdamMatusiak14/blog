package ad.java.blog.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ad.blog.security.JwtAuthFilter;
import ad.blog.security.JwtTokenProvider;
import ad.blog.service.CustomDetailsService;
import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private CustomDetailsService userDetailsService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Mock
    UserDetails userDetails;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    @BeforeEach
        void setUp() {
                jwtAuthFilter = new JwtAuthFilter(jwtTokenProvider, userDetailsService);
        }
    


 @Test
void doFilterInternal_TokenGood() throws Exception {
       
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Authorization")));
        when(jwtTokenProvider.validateToken("validToken")).thenReturn(true);
        when(jwtTokenProvider.extractUsername("validToken")).thenReturn("User");
        when(userDetailsService.loadUserByUsername("User")).thenReturn(userDetails);
        when(jwtTokenProvider.validateToken("validToken")).thenReturn(true);
        when(jwtTokenProvider.extractUsername("validToken")).thenReturn("User");
        when(userDetailsService.loadUserByUsername("User")).thenReturn(userDetails);
        
         
        jwtAuthFilter.doFilter(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        verify(filterChain, times(1)).doFilter(request, response);

}

@Test
void doFilterInternal_TokenNotExist() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.emptyList()));

        jwtAuthFilter.doFilter(request, response, filterChain);

         assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);



}

@Test
void doFilterInternal_TokenInvalid() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Authorization")));
        when(jwtTokenProvider.validateToken("invalidToken")).thenReturn(false);

        jwtAuthFilter.doFilter(request, response, filterChain);

        assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);

}

@Test
void doFilterInternal_TokenExpired() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer expiredToken");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Authorization")));
        when(jwtTokenProvider.validateToken("expiredToken")).thenReturn(false);

        jwtAuthFilter.doFilter(request, response, filterChain);

        assertEquals(null, SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);

}
}
