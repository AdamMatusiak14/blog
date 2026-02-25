package ad.java.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.blog.controller.UserController;
import ad.blog.model.AppUser;
import ad.blog.service.UserService;
import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    AppUser user1 = new AppUser(1L,"user1", "password1", "ROLE_USER");
    AppUser user2 = new AppUser(2L, "user2", "password2", "ROLE_USER");
    List<AppUser> users = Arrays.asList(user1, user2);


    @Test
    void testGetAllUsers() {
        
    
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        List<AppUser> actualUsers = userController.getAllUsers();

        // Assert
        assertEquals(users, actualUsers);
    }

    @Test
    void testCreateUser() {

        when(userService.createUser(user1)).thenReturn(user1);

        // Act
        AppUser createdUser = userController.createUser(user1);

        // Assert
        assertEquals(user1, createdUser);
    }
}
