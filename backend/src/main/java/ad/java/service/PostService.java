package ad.java.service;

import org.springframework.stereotype.Service;

import ad.java.model.Post;
import ad.java.repository.PostRepository;

@Service
public class PostService {
    

    private PostRepository postRepository;

    public PostService (PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post addPost(Post post){
       return postRepository.save(post);
    }

}
