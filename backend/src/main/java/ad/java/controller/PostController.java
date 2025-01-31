package ad.java.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.java.model.Post;
import ad.java.service.PostService;


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
    System.out.println("Jestem Controllerem Post");
    Post savePost = postService.addPost(post);
    return ResponseEntity.ok(savePost);
}

}
   
    

