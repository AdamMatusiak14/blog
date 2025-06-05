package ad.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ad.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId);
    // Additional query methods can be defined here if needed

    
}
