package ad.blog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.model.Comment;
import ad.blog.service.CommentService;

@CrossOrigin(origins = "http://locahost:3000") 
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, @PathVariable Long postId) {
        Comment saveComment = commentService.addComment(comment, postId);
        return ResponseEntity.ok(saveComment); 
        
}
}
