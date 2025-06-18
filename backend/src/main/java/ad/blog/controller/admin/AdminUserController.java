package ad.blog.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ad.blog.DTO.UserDTO;
import ad.blog.model.AppUser;
import ad.blog.model.Post;
import ad.blog.service.PostService;
import ad.blog.service.UserService;

@RestController
@RequestMapping("api/admin/users") 
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

   public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping 
    public List<UserDTO> getAllUserDTO(){
    List <UserDTO> users =  userService.getAllUsersDTO();
    
    return users;
    
}
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.noContent().build(); 

    }


}