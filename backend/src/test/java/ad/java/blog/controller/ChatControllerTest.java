package ad.java.blog.controller;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.aspectj.bridge.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.blog.DTO.ChatMessageDTO;
import ad.blog.controller.ChatController;
import ad.blog.model.AppUser;
import ad.blog.service.MessageService;
import ad.blog.service.UserService;


@ExtendWith(MockitoExtension.class)
public class ChatControllerTest {

    @Mock
    MessageService messageService;

    @Mock
    UserService userService;

    @InjectMocks
    ChatController chatController;

    private  ChatMessageDTO chatMessageDTO;
    private  AppUser sender;
    private AppUser receiver;
    private String senderUsername = "sender";
    private String receiverUsername = "receiver";

    @BeforeEach
    void setUp() {
        chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setSenderUsername("sender");
        chatMessageDTO.setReceiverUsername("receiver");
        chatMessageDTO.setContent("Hello!");

        sender = new AppUser();
        sender.setUsername("sender");

        receiver = new AppUser();
        receiver.setUsername("receiver");

    }

    @Test
    void testSendMessage() {
        // Mock the userService to return the sender and receiver
        when(userService.findByUsername("sender")).thenReturn(sender);
        when(userService.findByUsername("receiver")).thenReturn(receiver);

        // Call the method under test
        chatController.sendMessage(chatMessageDTO);

        // Verify that the messageService.saveMessage() method was called with a ChatMessage that has the correct sender, receiver, and content
        verify(messageService).saveMessage(argThat(chatMessage -> 
            chatMessage.getSender().equals(sender) &&
            chatMessage.getReceiver().equals(receiver) &&
            chatMessage.getContent().equals("Hello!")
        ));
    }

    @Test
    void testGetChatBetween() {
        when(userService.findByUsername("sender")).thenReturn(sender);
        when(userService.findByUsername("receiver")).thenReturn(receiver);

        chatController.getChatBetween("sender", "receiver");

        verify(messageService).getChatBetween(sender, receiver);
     
    }

        @Test
        void testGetAllMessagesForUser() {
            when(userService.findByUsername("sender")).thenReturn(sender);

            chatController.getAllMessagesForUser("sender");

            verify(messageService).getAllMessagesForUser(sender);
        }

    
}
