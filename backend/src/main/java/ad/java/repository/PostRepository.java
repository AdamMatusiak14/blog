package ad.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ad.java.model.Post;

@Repository
public interface PostRepository extends JpaRepository <Post, Long>   {
    
    
}
