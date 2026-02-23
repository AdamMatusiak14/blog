package ad.java.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.aspectj.bridge.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.blog.model.AppUser;
import ad.blog.model.ChatMessage;
import ad.blog.repository.ChatRepository;
import ad.blog.service.MessageService;
import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    ChatRepository chatRepository;

    @InjectMocks
    MessageService messageService;

    ChatMessage message;
    AppUser user1;
    AppUser user2;

    @BeforeEach
    void setUp() {
       
        user1 = new AppUser();
        user1.setId(1L);
        user1.setUsername("John");
        user1.setRole("USER");

        user2 = new AppUser();
        user2.setId(2L);
        user2.setUsername("Alice");
        user2.setRole("USER");

         message = new ChatMessage();
        message.setId(1L);
        message.setContent("Hello, how are you?");
        message.setTimestamp(LocalDateTime.now());
        message.setSender(user1);
        message.setReceiver(user2);


    }

@Test
void saveMessage_Success() {
    when(chatRepository.save(message)).thenReturn(message);
    ChatMessage savedMessage = messageService.saveMessage(message);
    assertEquals(message, savedMessage);    
    
}

@Test
void getChatBetween_Success() {
    when(chatRepository.findBySenderAndReceiver(user1, user2)).thenReturn(java.util.List.of(message));
    var chat = messageService.getChatBetween(user1, user2);
    assertEquals(1, chat.size());
    assertEquals(message, chat.get(0));
    assertEquals(message.getContent(), chat.get(0).getContent());
}

@Test
void getAllMessagesForUser_Success() {
    when(chatRepository.findBySenderOrReceiver(user1, user1)).thenReturn(java.util.List.of(message));
    var messages = messageService.getAllMessagesForUser(user1);
    assertEquals(1, messages.size());
    assertEquals(message, messages.get(0));
    assertEquals(message.getContent(), messages.get(0).getContent());

}
}