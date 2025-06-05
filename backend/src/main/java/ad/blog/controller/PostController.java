package ad.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.model.Post;
import ad.blog.service.PostService;


@CrossOrigin(origins = "http://locahost:3000") 
@RestController
@RequestMapping("/api/posts")
public class PostController {                   

private PostService postService;

public PostController(PostService postService){
    this.postService = postService;
}

@PostMapping
public ResponseEntity<Post> addPost(@RequestBody Post post){
    Post savePost = postService.addPost(post);
    return ResponseEntity.ok(savePost);
}

@GetMapping
public List<Post> getAllPosts()
{
    System.out.println("Tu metoda getAllPosts()");
    List <Post> posts =  postService.getAllPosts();
    System.out.println("Posts: " + posts);
    return posts;
    
}

@GetMapping ({"/{id}"})
public ResponseEntity<Post> getPostById(@PathVariable Long id){
    Optional<Post> post = postService.getPostById(id);
    return post.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
} 

@PostMapping("/{id}/like")
public ResponseEntity<Post> addLike(@PathVariable Long id) {
    Optional<Post> postOptional = postService.getPostById(id);
    if (postOptional.isPresent()) {
        Post post = postOptional.get();
        post.setLikesCount(post.getLikesCount() + 1);
        postService.addPost(post);
        return ResponseEntity.ok(post);
    }
    return ResponseEntity.notFound().build();
}


@PostMapping("/{id}/dislike")
public ResponseEntity<Post> addDisike(@PathVariable Long id) {
    Optional<Post> postOptional = postService.getPostById(id);
    if (postOptional.isPresent()) {
        Post post = postOptional.get();
        post.setDislike(post.getDislike() + 1);
        postService.addPost(post);
        return ResponseEntity.ok(post);
    }
    return ResponseEntity.notFound().build();
}
}
   
    

