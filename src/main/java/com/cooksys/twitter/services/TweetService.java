package com.cooksys.twitter.services;


import com.cooksys.twitter.dtos.*;
import java.util.List;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.TweetRequestDto;
import com.cooksys.twitter.dtos.TweetResponseDto;

public interface TweetService {
    List<TweetResponseDto> getTweets();

    TweetResponseDto postTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto getTweetById(Long id);

    TweetResponseDto deleteTweetById(Long id, CredentialsDto credentialsRequestDto);

    void postTweetLike(Long id, CredentialsDto credentialsDto);

    TweetResponseDto postTweetReply(Long id, TweetRequestDto tweetRequestDto);

    TweetResponseDto postTweetRepost(Long id, CredentialsDto credentialsDto, TweetRequestDto tweetRequestDto);

    List<HashtagDto> getTweetsTags(Long id);

    List<UserResponseDto> getTweetsLikes(Long id);

    ContextDto getTweetContext(Long id, ContextDto contextDto);

    List<TweetResponseDto> getTweetReplies(Long id);

    List<TweetResponseDto> getTweetReposts(Long id);

    List<UserResponseDto> getTweetMentions(Long id);
}
