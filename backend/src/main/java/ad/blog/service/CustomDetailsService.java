package ad.blog.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ad.blog.model.AppUser;
import ad.blog.repository.AppUserRespository;


@Service
public class CustomDetailsService implements UserDetailsService{
    
    private final AppUserRespository appUserRespository;

    public CustomDetailsService (AppUserRespository appUserRespository){
        this.appUserRespository = appUserRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        AppUser user = appUserRespository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found: "+ username);
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
       
    }
    
}
