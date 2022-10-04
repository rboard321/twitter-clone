package com.cooksys.twitter.services;

import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.User;

public interface ValidateService
{
    boolean validateTagExists(String label, Hashtag hashtag);

    boolean validateUsernameExists(String label, User user);

    boolean validateUsernameAvailable(String label, User user);
}
