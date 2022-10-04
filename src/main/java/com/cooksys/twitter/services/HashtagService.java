package com.cooksys.twitter.services;

import java.util.List;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Hashtag;

public interface HashtagService {

    List<Hashtag> getAllTags();

    List<TweetResponseDto> getTweetsByTag(String label);


}