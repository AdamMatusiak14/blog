package ad.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ad.blog.model.AppUser;
import ad.blog.model.ChatMessage;

public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
  

    List<ChatMessage> findBySenderAndReceiver(AppUser sender, AppUser receiver);

    List<ChatMessage> findBySenderOrReceiver(AppUser sender, AppUser receiver);
}
