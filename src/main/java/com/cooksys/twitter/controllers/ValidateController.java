package com.cooksys.twitter.controllers;

import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.User;
import com.cooksys.twitter.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;

    @GetMapping("/tag/exists/{label}")
    public boolean validateTagExists(@PathVariable String label, Hashtag hashtag){
        return validateService.validateTagExists(label, hashtag);
    }

    @GetMapping("/username/exists/@{username}")
    public boolean validateUsernameExists(@PathVariable String username, User user){
        return validateService.validateUsernameExists(username, user);
    }

    @GetMapping("/username/available/@{username}")
    public boolean validateUsernameAvailable(@PathVariable String username, User user){
        return validateService.validateUsernameAvailable(username, user);
    }

}
