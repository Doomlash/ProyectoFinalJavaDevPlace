package group3.mvc.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import group3.mvc.model.MyUser;
import group3.mvc.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> optional = ur.findByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        MyUser u = optional.get();

        Set<GrantedAuthority> set = new HashSet<>();
        set.add(new SimpleGrantedAuthority(u.getRole()));
        return new User(u.getUsername(), u.getPassword(), set);
    }
    
}
