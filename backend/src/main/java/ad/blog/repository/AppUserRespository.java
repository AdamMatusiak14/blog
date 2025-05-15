package ad.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ad.blog.model.AppUser;
import java.util.List;



@Repository
public interface AppUserRespository extends JpaRepository <AppUser, Long> {

    Optional<AppUser> findUserByUsername(String username);
    AppUser findByUsername(String username);
    
    
} 