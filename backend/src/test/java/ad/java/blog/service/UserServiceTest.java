package ad.java.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ad.blog.DTO.UserDTO;
import ad.blog.model.AppUser;
import ad.blog.repository.AppUserRespository;
import ad.blog.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    AppUserRespository userRespository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @InjectMocks
    UserService userService;

    AppUser user;
    AppUser user2;
    UserDTO userDTO;
    UserDTO userDTO2;
    
    @BeforeEach
    void setUp(){
        user = new AppUser();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("USER");

        user2 = new AppUser();
        user2.setId(2L);
        user2.setUsername("testuser2");
        user2.setPassword("password2");
        user2.setRole("USER");

        userDTO = new UserDTO(1L, "testuser", "USER");
        userDTO2 = new UserDTO(2L, "testuser2", "USER");
    }
    


    @Test 
    void getAllUsers_Succes(){
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(authentication.getName()).thenReturn("testuser");
    when(userRespository.findAll()).thenReturn(List.of(user, user2));

    List<AppUser> users =  userService.getAllUsers();

    assert(users.size() == 1);
    assert(users.get(0).getUsername().equals("testuser2"));
    
    }

    @Test
    void getAllUsers_AuthenticationisNull()
    {
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);
        

        assertThrows(IllegalStateException.class, () -> 
            userService.getAllUsers(),
            "No authenticated user found"
        );
    }

    @Test
    void getAllUsersDTO_Succes(){
         when(securityContext.getAuthentication()).thenReturn(authentication);
         SecurityContextHolder.setContext(securityContext);
       
      
        when(userRespository.findAll()).thenReturn(List.of(user, user2));
        List<AppUser> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
      

        List<UserDTO> usersDTO =  users.stream().map(u -> new UserDTO(u.getId(), u.getUsername(), u.getRole())).toList();   
        List<UserDTO> result = userService.getAllUsersDTO();

        assertEquals(usersDTO.size(), result.size());
        assertEquals(usersDTO.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(usersDTO.get(1).getUsername(), result.get(1).getUsername());
        
    }

   @Test
   void findByUsername_Succes(){
    when(userRespository.findByUsername("testuser")).thenReturn(user);
    AppUser result = userService.findByUsername("testuser");
    assertEquals(user.getUsername(), result.getUsername());
   }

    @Test
    void findByUsername_UserNotFound(){
        when(userRespository.findByUsername("nonexistent")).thenReturn(null);
        AppUser result = userService.findByUsername("nonexistent");
        assertEquals(null, result);
    }

    @Test
    void createUser_Succes(){
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRespository.save(user)).thenReturn(user);
        AppUser result = userService.createUser(user);
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void createUser_PasswordEncodingFails(){
        when(passwordEncoder.encode("password")).thenThrow(new RuntimeException("Encoding failed"));
        assertThrows(RuntimeException.class, () -> userService.createUser(user), "Encoding failed");
    }

    @Test
    void deleteUser_Succes(){
        when(userRespository.findById(1L)).thenReturn(java.util.Optional.of(user));
        userService.deleteUser(1L);

        verify(userRespository).delete(user);

       
    }

    @Test
    void deleteUser_UserNotFound(){
        when(userRespository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L), "User not found");
        verify(userRespository, never()).delete(any());
    }

}

