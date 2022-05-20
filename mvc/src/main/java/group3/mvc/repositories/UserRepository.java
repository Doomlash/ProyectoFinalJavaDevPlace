package group3.mvc.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import group3.mvc.model.MyUser;

@Repository
public class UserRepository {

    public Optional<MyUser> findByUsername(String username) {
        return Optional.of(new MyUser(username, "123", "admin"));
    }
    
}
