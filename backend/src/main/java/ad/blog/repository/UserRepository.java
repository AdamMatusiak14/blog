package ad.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ad.blog.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
  
    
    
}
