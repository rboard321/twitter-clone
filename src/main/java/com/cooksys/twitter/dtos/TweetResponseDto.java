package com.cooksys.twitter.dtos;

import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
public class TweetResponseDto {
    private Long id;

    private User author;

    private Boolean deleted;

    private Timestamp posted;


    private String content;

    private Tweet inReplyTo;

    private Tweet repostOf;

    private List<Tweet> reposts;

    private List<Tweet> replies;

    private List<User> likes;

    private List<Hashtag> hashtags;

    private List<User> userMentioned;
}
