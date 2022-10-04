package com.cooksys.twitter.repositories;

import com.cooksys.twitter.entities.Credentials;
import com.cooksys.twitter.entities.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByCredentials_Username(String username);

    Optional<User> findByCredentials(Credentials credentials);

    User getByCredentials_UsernameAndCredentials_Password(String username, String password);
}
