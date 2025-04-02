package ad.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ad.blog.model.Post;
import ad.blog.repository.PostRepository;

@Service
public class PostService {
    

    private PostRepository postRepository;

    public PostService (PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public Post addPost(Post post){
       return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Optional <Post> getPostById(Long id){
        return postRepository.findById(id);
    }

}
