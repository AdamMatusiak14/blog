package ad.blog.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.model.Post;
import ad.blog.service.PostService;


@RestController
@RequestMapping("api/admin/posts")
@PreAuthorize("hasRole('ADMIN')")
public class AdminPostController {

      private final PostService postService;

    public AdminPostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping 
    public List<Post> getAllPosts(){
    List <Post> posts =  postService.getAllPosts();
    return posts;
    
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id); // zaimplementowć
        return ResponseEntity.noContent().build();
    }
    
}
