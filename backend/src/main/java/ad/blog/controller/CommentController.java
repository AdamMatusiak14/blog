package ad.blog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.DTO.CommentDTO;
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
    public ResponseEntity<CommentDTO> addComment(@RequestBody Comment comment, @PathVariable Long postId) {
        Comment saveComment = commentService.addComment(comment, postId);
      CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(saveComment.getId());  
        commentDTO.setContent(saveComment.getContent());
        commentDTO.setAuthor(saveComment.getAuthor().getUsername());
        return ResponseEntity.ok(commentDTO); 
        
}

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDTO>>getCommentsByPostId(@PathVariable Long postId) { 
        List<Comment> comments = commentService.getCommentsByPostId(postId); 
       

        List<CommentDTO> commentDTOs = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setAuthor(comment.getAuthor().getUsername());
            return commentDTO;
        }).toList();
        return ResponseEntity.ok(commentDTOs); 
    }
}
