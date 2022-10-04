package com.cooksys.twitter.controllers;


import com.cooksys.twitter.dtos.*;
import com.cooksys.twitter.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getTweets(){
        return tweetService.getTweets();
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable Long id){
        return tweetService.getTweetById(id);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTweetsTags(@PathVariable Long id){
        return tweetService.getTweetsTags(id);
    }

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getTweetsLikes(@PathVariable Long id){
        return tweetService.getTweetsLikes(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getTweetContext(@PathVariable Long id, ContextDto contextDto) {
    	System.out.println(tweetService.getTweetContext(id, contextDto));
        return tweetService.getTweetContext(id, contextDto);
        
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getTweetReplies(@PathVariable Long id) {
        return tweetService.getTweetReplies(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getTweetReposts(@PathVariable Long id) {
        return tweetService.getTweetReposts(id);
    }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getTweetMentions(@PathVariable Long id) {
        return tweetService.getTweetMentions(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto postTweet(@RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.postTweet(tweetRequestDto);
    }

    @PostMapping("/{id}/like")
    @ResponseStatus(HttpStatus.OK)
    public void postTweetLike(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto){
        tweetService.postTweetLike(id, credentialsDto);
    }

    @PostMapping("/{id}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto postTweetReply(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.postTweetReply(id, tweetRequestDto);
    }

    @PostMapping("/{id}/repost")
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponseDto postTweetRepost(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto, TweetRequestDto tweetRequestDto){
        return tweetService.postTweetRepost(id, credentialsDto, tweetRequestDto);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto){
        return tweetService.deleteTweetById(id, credentialsDto);
    }

}
