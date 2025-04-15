package ad.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ad.blog.model.AppUser;
import ad.blog.model.ChatMessage;
import ad.blog.repository.ChatRepository;

@Service
public class MessageService {

    
    private final ChatRepository chatRepository;

    public MessageService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatRepository.save(message);
    }

    public List<ChatMessage> getChatBetween(AppUser user1, AppUser user2) {
        return chatRepository.findBySenderAndReceiver(user1, user2);
    }

    public List<ChatMessage> getAllMessagesForUser(AppUser user) {
        return chatRepository.findBySenderOrReceiver(user, user);
    }
    
}
