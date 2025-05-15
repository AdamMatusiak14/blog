package ad.blog.config;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import ad.blog.security.JwtTokenProvider;
import ad.blog.security.JwtTokenProvider;

@Component
public class CustomHandhsakeInterceptor implements HandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public CustomHandhsakeInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

   

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {


        String token = request.getURI().getQuery();
        System.out.println("Interceptor token: " + token);
       
        if (token != null && token.startsWith("token=")) {
            token = token.substring("token=".length());
        }
        
        //JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        if(token != null && jwtTokenProvider.validateToken(token)) {
            System.out.println("Zwraca true");
            return true;    }
            return false;
        }

 
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'afterHandshake'");
    }

    
    
}
