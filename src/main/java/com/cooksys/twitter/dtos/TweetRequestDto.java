package com.cooksys.twitter.dtos;

import com.cooksys.twitter.entities.Credentials;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
public class TweetRequestDto {

    private User author;

    private Timestamp posted;

    private Boolean deleted;

    private String content;

    private Credentials credentials;

    private Tweet inReplyTo;

    private Tweet repostOf;

    private List<Tweet> reposts;

    private List<Tweet> replies;

    private List<User> likes;

    private List<Hashtag> hashtags;

    private List<User> userMentioned;
}
