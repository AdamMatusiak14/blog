package ad.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ad.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Additional query methods can be defined here if needed
    
}
