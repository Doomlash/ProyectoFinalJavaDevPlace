package otakus_de_la_costa.grupo3.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import otakus_de_la_costa.grupo3.database.AuthenticationJPA;
import otakus_de_la_costa.grupo3.database.RoleJPA;
import otakus_de_la_costa.grupo3.repositories.LogInRepository;

public class MyUserDetailsService implements UserDetailsManager, UserDetailsPasswordService{
    @Autowired
    private LogInRepository lr;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthenticationJPA> optional = lr.findById(username);
        if(optional.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }
        AuthenticationJPA auth = optional.get();
        Collection<SimpleGrantedAuthority> c = new ArrayList<>();
        c.add(new SimpleGrantedAuthority(auth.getRole().getName()));
        UserDetails user = User.builder()
                            .username(auth.getUsername())
                            .password(auth.getPassword())
                            .roles(auth.getRole().getName())
                            .build();
        return user;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        lr.updatePassword(user.getUsername(),user.getPassword(),newPassword);
        return loadUserByUsername(user.getUsername());
    }

    @Override
    public void createUser(UserDetails user) {
        if(!userExists(user.getUsername())){
            AuthenticationJPA auth = new AuthenticationJPA();
            auth.setUsername(user.getUsername());
            auth.setPassword(user.getPassword());
            auth.setRole(new RoleJPA(2));
            lr.save(auth);
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        if(userExists(user.getUsername())){
            AuthenticationJPA auth = new AuthenticationJPA();
            auth.setUsername(user.getUsername());
            auth.setPassword(user.getPassword());
            auth.setRole(new RoleJPA(2));
            lr.save(auth);
        }
    }

    @Override
    public void deleteUser(String username) {
        if(userExists(username)){
            lr.deleteById(username);
        }
        
    }


    @Override
    public boolean userExists(String username) {
        return lr.findById(username).isPresent();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        lr.updatePassword(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(),oldPassword,newPassword);
    }
    
}
