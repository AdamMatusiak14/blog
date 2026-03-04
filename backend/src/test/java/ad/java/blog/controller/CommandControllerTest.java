package ad.java.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.blog.DTO.CommentDTO;
import ad.blog.controller.CommentController;
import ad.blog.model.AppUser;
import ad.blog.model.Comment;
import ad.blog.model.Post;
import ad.blog.service.CommentService;

@ExtendWith(MockitoExtension.class)
public class CommandControllerTest {

    @Mock
    private CommentService commentService;  

    @InjectMocks
    private CommentController commentController;
    
    private Comment comment1;
    private Comment comment2;
    private List<Comment> comments;

    @BeforeEach
    void setUp(){

        Post post = new Post();
        AppUser author = new AppUser();
        author.setId(1L);
        author.setUsername("testuser");
        author.setPassword("password");

        
        comment1 = new Comment();
        comment1.setId(1L);
        comment1.setContent("This is the first comment");
        comment1.setPost(post);
        comment1.setAuthor(author);

        comment2 = new Comment();
        comment2.setId(2L);
        comment2.setContent("This is the second comment");
        comment2.setPost(post);
        comment2.setAuthor(author);

        comments = List.of(comment1, comment2);
       
    }


    @Test
    void testAddComment() {
       
        when(commentService.addComment(any(),eq(1L))).thenReturn(comment1);

        CommentDTO result = commentController.addComment(comment1, 1L).getBody();

       assertEquals(comment1.getId(), result.getId());
        assertEquals(comment1.getContent(), result.getContent());
        assertEquals(comment1.getAuthor().getUsername(), result.getAuthor());   
    
    }
    
    @Test
    void testCommentsByPostId() {
        when(commentService.getCommentsByPostId(anyLong())).thenReturn(comments);

        List<CommentDTO> result = commentController.getCommentsByPostId(1L).getBody();

        assertEquals(2, result.size());
        assertEquals(comments.get(0).getId(), result.get(0).getId());
        assertEquals(comments.get(0).getContent(), result.get(0).getContent());
        assertEquals(comments.get(0).getAuthor().getUsername(), result.get(0).getAuthor());

        assertEquals(comments.get(1).getId(), result.get(1).getId());
        assertEquals(comments.get(1).getContent(), result.get(1).getContent());
        assertEquals(comments.get(1).getAuthor().getUsername(), result.get(1).getAuthor());
    }
}
