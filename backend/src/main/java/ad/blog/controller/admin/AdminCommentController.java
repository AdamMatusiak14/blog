package ad.blog.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.DTO.CommentDTO;
import ad.blog.model.Comment;
import ad.blog.service.CommentService;


@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCommentController {

    
    private final CommentService commentService;

    public AdminCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/admin/comments")
    public List<CommentDTO> getAllComments() { 
        
        List<Comment> comemnts = commentService.getAllComments();
       List <CommentDTO> comemntsDTO = comemnts.stream()
                .map(comment -> new CommentDTO(comment.getId(), comment.getContent(), comment.getAuthor().getUsername()))
                .toList();
           return comemntsDTO;  
    }

    @DeleteMapping("/admin/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        System.out.println("Deleting comment with id: " + id);
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
