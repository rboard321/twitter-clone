package com.cooksys.twitter.services.impl;

import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.User;
import com.cooksys.twitter.repositories.HashtagRepository;
import com.cooksys.twitter.repositories.UserRepository;
import com.cooksys.twitter.services.ValidateService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class: ValidateServiceImpl.java
 * Authors: Jordan Adkins, Ricky Board, Emmanuel Ekamby
 * Date: 8/31/22
 * About: This is the service that handles all endpoints /validate related.
 */
@Service
@RequiredArgsConstructor
@Setter
@Getter
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public boolean validateTagExists(String label, Hashtag hashtag) {
        hashtag = hashtagRepository.getByLabel(label);
        return hashtag != null;
    }

    @Override
    public boolean validateUsernameExists(String label, User user) {
        Optional<User> user1 = userRepository.findByCredentials_Username(label);
        return user1.isPresent();
    }

    @Override
    public boolean validateUsernameAvailable(String label, User user) {
        return !validateUsernameExists(label, user);
    }
}
