package ad.java.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ad.blog.controller.PostController;
import ad.blog.model.Post;
import ad.blog.service.PostService;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @InjectMocks
    PostController postController;

    private Post post1;
    private Post post2;
    private List<Post> posts = new ArrayList<>();
    // Optional post

    @BeforeEach
    void setUp(){
        post1 = new Post();
        post1.setId(1L);
        post1.setTitle("title1");
        post1.setContent("content1");

        post2 = new Post();
        post2.setId(2L);
        post2.setTitle("title2");
        post2.setContent("content2");

        posts.add(post1);
        posts.add(post2);
        
        
    }

    @Test
    void testGetAllPosts(){
        when(postService.getAllPosts()).thenReturn(posts);
        List<Post> result = postController.getAllPosts();
        assertEquals(2, result.size());
        assertEquals("title1", result.get(0).getTitle());
        assertEquals("title2", result.get(1).getTitle());
    }

    @Test
    void testGetPostById_Succes(){
        when(postService.getPostById(1L)).thenReturn(java.util.Optional.of(post1));
        ResponseEntity<Post> response = postController.getPostById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("title1", response.getBody().getTitle());
    }

    @Test
    void testGetPostById_NotFound(){
        when(postService.getPostById(3L)).thenReturn(java.util.Optional.empty());
        ResponseEntity<Post> response = postController.getPostById(3L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testAddLike_Found(){
        when(postService.getPostById(1L)).thenReturn(java.util.Optional.of(post1));
        ResponseEntity<Post> response = postController.addLike(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getLikesCount());
    }

    @Test
    void testAddLike_NotFound(){
        when(postService.getPostById(3L)).thenReturn(java.util.Optional.empty());
        ResponseEntity<Post> response = postController.addLike(3L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testAddDislike_Found(){
        when(postService.getPostById(1L)).thenReturn(java.util.Optional.of(post1));
        ResponseEntity<Post> response = postController.addLike(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getLikesCount());
    }

    @Test
    void testAddDislike_NotFound(){
        when(postService.getPostById(3L)).thenReturn(java.util.Optional.empty());
        ResponseEntity<Post> response = postController.addLike(3L);
        assertEquals(404, response.getStatusCodeValue());
    }
    

}