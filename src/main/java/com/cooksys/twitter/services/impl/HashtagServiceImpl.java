package com.cooksys.twitter.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.exceptions.BadRequestException;
import com.cooksys.twitter.exceptions.NotFoundException;
import com.cooksys.twitter.mappers.TweetMapper;
import com.cooksys.twitter.repositories.HashtagRepository;
import com.cooksys.twitter.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService{

    private final HashtagRepository hashtagRepository;
    private final TweetMapper tweetMapper;

    @Override
    public List<Hashtag> getAllTags() {

        return returnAllHashtags();
    }

    private List<Hashtag> returnAllHashtags() {
        List<Hashtag> allTags = hashtagRepository.findAll();
        return allTags;
    }



    @Override
    public List<TweetResponseDto> getTweetsByTag(String label) {
        if(label == null) {
            throw new BadRequestException("Sorry you must include a hashtag.");
        }

        Hashtag hashtag = hashtagRepository.getByLabel(label);

        if (hashtag == null)
            throw new NotFoundException("No hashtag with label " + label + " found.");

        List<Tweet> tweetsToGet = hashtag.getTweets();
        List<Tweet> tweetsToReturn = new ArrayList<>();

        for(Tweet t: tweetsToGet) {
            if(!t.isDeleted() && !t.getContent().isEmpty()) {
                tweetsToReturn.add(t);
                setAuthorUsername(t);
            }
        }
        tweetsToGet.sort(Comparator.comparing(Tweet::getPosted).reversed());
        return tweetMapper.entitiesToDtos(tweetsToReturn);
    }

    private void setAuthorUsername(Tweet t) {
        t.getAuthor().setUsername(t.getAuthor().getCredentials().getUsername());
    }

    private void setAuthorUsername(List<Tweet> list) {
       for (Tweet t : list){
           t.getAuthor().setUsername(t.getAuthor().getCredentials().getUsername());
       }
    }


}