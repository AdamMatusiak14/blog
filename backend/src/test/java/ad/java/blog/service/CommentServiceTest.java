package ad.java.blog.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.blog.repository.CommentRepository;
import ad.blog.repository.PostRepository;
import ad.blog.repository.UserRepository;
import ad.blog.service.CommentService;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    

    @Mock
    CommentRepository commentRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CommentService commentService;

    void setUp() {
     
    }
}
