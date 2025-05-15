package ad.blog.controller;


import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import ad.blog.DTO.ChatMessageDTO;
import ad.blog.model.AppUser;
import ad.blog.model.ChatMessage;
import ad.blog.service.MessageService;
import ad.blog.service.UserService;

@Controller
public class ChatController {

    private final MessageService messageService;
    private final UserService userService;

    public ChatController(MessageService messageService, UserService userService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(@Payload ChatMessageDTO chatMessageDTO) {

        System.out.println("Sender: " + chatMessageDTO.getSenderUsername());    
        System.out.println("Receiver: " + chatMessageDTO.getReceiverUsername());
        System.out.println("Content: " + chatMessageDTO.getContent());

        AppUser sender = userService.findByUsername(chatMessageDTO.getSenderUsername());
        AppUser receiver = userService.findByUsername(chatMessageDTO.getReceiverUsername());        

        ChatMessage chatMessage = new ChatMessage(sender, receiver, chatMessageDTO.getContent()); // timestamp is set in the constructor

    
        return messageService.saveMessage(chatMessage);
    }
}
