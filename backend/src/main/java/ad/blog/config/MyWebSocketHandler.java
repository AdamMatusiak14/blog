package ad.blog.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

public class MyWebSocketHandler implements WebSocketHandler {
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Tutaj można dodać logikę po nawiązaniu połączenia (np. zapisanie sesji)
        System.out.println("Połączenie nawiązane z klientem: " + session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // Odbieranie wiadomości od klienta
        String msg = (String) message.getPayload();
        System.out.println("Odebrano wiadomość: " + msg);
        
        // Możesz wysłać odpowiedź do klienta
        session.sendMessage(new TextMessage("Wiadomość odebrana: " + msg));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // Obsługa błędów transportu (np. problem z połączeniem)
        System.err.println("Błąd transportu: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // Logika po zamknięciu połączenia
        System.out.println("Połączenie zamknięte: " + session.getId());
    }

    @Override
    public boolean supportsPartialMessages() {
        // Czy obsługiwane są częściowe wiadomości (w większości przypadków zwracamy true)
        return false;
    }
}
