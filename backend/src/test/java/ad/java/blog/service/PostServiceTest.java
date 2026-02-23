package ad.java.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.blog.model.Post;
import ad.blog.repository.PostRepository;
import ad.blog.service.PostService;
import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepository postRepository;
    
    @InjectMocks
    PostService postService;

    Post post;
    Post post2;
    List<Post> posts;

    @BeforeEach
    void setUp(){
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setContent("This is a test post.");

        post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Test Post 2");
        post2.setContent("This is another test post.");

        posts = List.of(post, post2);
    }

    @Test
    void testAddPost(){
        when(postRepository.save(post)).thenReturn(post);

        postService.addPost(post);

        assertEquals(post.getId(), 1L);
        assert(post.getTitle().equals("Test Post"));    
        assert(post.getContent().equals("This is a test post."));
        
}

@Test
void testAddPostNull(){
    when(postRepository.save(null)).thenReturn(null);

    var result = postService.addPost(null);

    assert(result == null);
}

@Test
void testGetAllPosts(){
    when(postRepository.findAll()).thenReturn(List.of(post));

    var posts = postService.getAllPosts();

    assertEquals(1, posts.size());
    assertEquals(post.getId(), posts.get(0).getId());
    assert(posts.get(0).getTitle().equals("Test Post"));
    assert(posts.get(0).getContent().equals("This is a test post."));

}

@Test
void testGetAllPostsEmpty(){
    when(postRepository.findAll()).thenReturn(List.of());

    var posts = postService.getAllPosts();

    assert(posts.isEmpty());
}   


@Test
void testGetPostById(){
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));

    var foundPost = postService.getPostById(1L);

    assert(foundPost.isPresent());
    assertEquals(post.getId(), foundPost.get().getId());
    assert(foundPost.get().getTitle().equals("Test Post"));
    assert(foundPost.get().getContent().equals("This is a test post."));
}

@Test
void testGetPostByIdNotFound(){
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.empty());

    var foundPost = postService.getPostById(1L);

    assert(foundPost.isEmpty());
}


@Test
void testDeletePost(){
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));
    postService.deletePost(1L);
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.empty());
    var deletedPost = postService.getPostById(1L);
    assert(deletedPost.isEmpty());
    verify(postRepository).deleteById(1L); 

}

@Test
void testDeletePostNotFound(){
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.empty());
    postService.deletePost(1L);
    when(postRepository.findById(1L)).thenReturn(java.util.Optional.empty());
    var deletedPost = postService.getPostById(1L);
    assert(deletedPost.isEmpty());
    verify(postRepository).deleteById(1L);



}

}