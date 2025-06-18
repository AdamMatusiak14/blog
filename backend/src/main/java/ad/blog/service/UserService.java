package ad.blog.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ad.blog.DTO.UserDTO;
import ad.blog.model.AppUser;
import ad.blog.repository.AppUserRespository;





@Service
public class UserService {

AppUserRespository userRespository;
PasswordEncoder passwordEncoder;

public UserService(AppUserRespository userRespository, PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
    this.userRespository = userRespository;
}   

public List<AppUser> getAllUsers() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    List<AppUser> users =  userRespository.findAll(); 
    return users.stream().
    filter(user -> !user.getUsername().equals(username))
    .collect(Collectors.toList());

}

public List<UserDTO> getAllUsersDTO() {
    return getAllUsers().stream()
        .map(user -> new UserDTO(user.getId(), user.getUsername(), user.getRole()))
        .collect(Collectors.toList());
}

public AppUser findByUsername(String username) {
    return userRespository.findByUsername(username);

}

public AppUser createUser(AppUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRespository.save(user);  

}

public void deleteUser(Long id) {
    AppUser user = userRespository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    userRespository.delete(user);

}
}