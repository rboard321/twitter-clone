package com.cooksys.twitter.controllers;

import com.cooksys.twitter.services.HashtagService;
import lombok.RequiredArgsConstructor;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Hashtag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagsController {

    private final HashtagService tagService;

    @GetMapping
    public List<Hashtag> getAllTags(){
        return tagService.getAllTags();
    }

    @GetMapping("/{label}")
    public List<TweetResponseDto> getTweetsByTag(@PathVariable String label){
        return tagService.getTweetsByTag(label);
    }

}