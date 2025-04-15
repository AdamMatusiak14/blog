package ad.blog.controller;


import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.model.AppUser;
import ad.blog.service.UserService;

@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/users")
public class UserController {

private UserService userService;

public UserController(UserService userService) {
    this.userService = userService; 
}

    @GetMapping
   public List<AppUser> getAllUsers() {

        return userService.getAllUsers();
    }
    
}
