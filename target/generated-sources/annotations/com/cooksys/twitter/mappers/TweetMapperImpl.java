package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.TweetRequestDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-22T12:33:13-0400",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class TweetMapperImpl implements TweetMapper {

    @Override
    public TweetResponseDto entityToDto(Tweet entity) {
        if ( entity == null ) {
            return null;
        }

        TweetResponseDto tweetResponseDto = new TweetResponseDto();

        tweetResponseDto.setAuthor( entity.getAuthor() );
        tweetResponseDto.setContent( entity.getContent() );
        tweetResponseDto.setDeleted( entity.isDeleted() );
        List<Hashtag> list = entity.getHashtags();
        if ( list != null ) {
            tweetResponseDto.setHashtags( new ArrayList<Hashtag>( list ) );
        }
        tweetResponseDto.setId( entity.getId() );
        tweetResponseDto.setInReplyTo( entity.getInReplyTo() );
        List<User> list1 = entity.getLikes();
        if ( list1 != null ) {
            tweetResponseDto.setLikes( new ArrayList<User>( list1 ) );
        }
        tweetResponseDto.setPosted( entity.getPosted() );
        List<Tweet> list2 = entity.getReplies();
        if ( list2 != null ) {
            tweetResponseDto.setReplies( new ArrayList<Tweet>( list2 ) );
        }
        tweetResponseDto.setRepostOf( entity.getRepostOf() );
        List<Tweet> list3 = entity.getReposts();
        if ( list3 != null ) {
            tweetResponseDto.setReposts( new ArrayList<Tweet>( list3 ) );
        }
        List<User> list4 = entity.getUserMentioned();
        if ( list4 != null ) {
            tweetResponseDto.setUserMentioned( new ArrayList<User>( list4 ) );
        }

        return tweetResponseDto;
    }

    @Override
    public List<TweetResponseDto> entitiesToDtos(List<Tweet> entities) {
        if ( entities == null ) {
            return null;
        }

        List<TweetResponseDto> list = new ArrayList<TweetResponseDto>( entities.size() );
        for ( Tweet tweet : entities ) {
            list.add( entityToDto( tweet ) );
        }

        return list;
    }

    @Override
    public Tweet requestDtoToEntity(TweetRequestDto questionRequestDto) {
        if ( questionRequestDto == null ) {
            return null;
        }

        Tweet tweet = new Tweet();

        tweet.setAuthor( questionRequestDto.getAuthor() );
        tweet.setContent( questionRequestDto.getContent() );
        if ( questionRequestDto.getDeleted() != null ) {
            tweet.setDeleted( questionRequestDto.getDeleted() );
        }
        List<Hashtag> list = questionRequestDto.getHashtags();
        if ( list != null ) {
            tweet.setHashtags( new ArrayList<Hashtag>( list ) );
        }
        tweet.setInReplyTo( questionRequestDto.getInReplyTo() );
        List<User> list1 = questionRequestDto.getLikes();
        if ( list1 != null ) {
            tweet.setLikes( new ArrayList<User>( list1 ) );
        }
        tweet.setPosted( questionRequestDto.getPosted() );
        List<Tweet> list2 = questionRequestDto.getReplies();
        if ( list2 != null ) {
            tweet.setReplies( new ArrayList<Tweet>( list2 ) );
        }
        tweet.setRepostOf( questionRequestDto.getRepostOf() );
        List<Tweet> list3 = questionRequestDto.getReposts();
        if ( list3 != null ) {
            tweet.setReposts( new ArrayList<Tweet>( list3 ) );
        }
        List<User> list4 = questionRequestDto.getUserMentioned();
        if ( list4 != null ) {
            tweet.setUserMentioned( new ArrayList<User>( list4 ) );
        }

        return tweet;
    }

    @Override
    public Tweet responseDtoToEntity(TweetResponseDto questionResponseDto) {
        if ( questionResponseDto == null ) {
            return null;
        }

        Tweet tweet = new Tweet();

        tweet.setAuthor( questionResponseDto.getAuthor() );
        tweet.setContent( questionResponseDto.getContent() );
        if ( questionResponseDto.getDeleted() != null ) {
            tweet.setDeleted( questionResponseDto.getDeleted() );
        }
        List<Hashtag> list = questionResponseDto.getHashtags();
        if ( list != null ) {
            tweet.setHashtags( new ArrayList<Hashtag>( list ) );
        }
        tweet.setId( questionResponseDto.getId() );
        tweet.setInReplyTo( questionResponseDto.getInReplyTo() );
        List<User> list1 = questionResponseDto.getLikes();
        if ( list1 != null ) {
            tweet.setLikes( new ArrayList<User>( list1 ) );
        }
        tweet.setPosted( questionResponseDto.getPosted() );
        List<Tweet> list2 = questionResponseDto.getReplies();
        if ( list2 != null ) {
            tweet.setReplies( new ArrayList<Tweet>( list2 ) );
        }
        tweet.setRepostOf( questionResponseDto.getRepostOf() );
        List<Tweet> list3 = questionResponseDto.getReposts();
        if ( list3 != null ) {
            tweet.setReposts( new ArrayList<Tweet>( list3 ) );
        }
        List<User> list4 = questionResponseDto.getUserMentioned();
        if ( list4 != null ) {
            tweet.setUserMentioned( new ArrayList<User>( list4 ) );
        }

        return tweet;
    }

    @Override
    public TweetRequestDto entityToRequestDto(Tweet tweet) {
        if ( tweet == null ) {
            return null;
        }

        TweetRequestDto tweetRequestDto = new TweetRequestDto();

        tweetRequestDto.setAuthor( tweet.getAuthor() );
        tweetRequestDto.setContent( tweet.getContent() );
        tweetRequestDto.setDeleted( tweet.isDeleted() );
        List<Hashtag> list = tweet.getHashtags();
        if ( list != null ) {
            tweetRequestDto.setHashtags( new ArrayList<Hashtag>( list ) );
        }
        tweetRequestDto.setInReplyTo( tweet.getInReplyTo() );
        List<User> list1 = tweet.getLikes();
        if ( list1 != null ) {
            tweetRequestDto.setLikes( new ArrayList<User>( list1 ) );
        }
        tweetRequestDto.setPosted( tweet.getPosted() );
        List<Tweet> list2 = tweet.getReplies();
        if ( list2 != null ) {
            tweetRequestDto.setReplies( new ArrayList<Tweet>( list2 ) );
        }
        tweetRequestDto.setRepostOf( tweet.getRepostOf() );
        List<Tweet> list3 = tweet.getReposts();
        if ( list3 != null ) {
            tweetRequestDto.setReposts( new ArrayList<Tweet>( list3 ) );
        }
        List<User> list4 = tweet.getUserMentioned();
        if ( list4 != null ) {
            tweetRequestDto.setUserMentioned( new ArrayList<User>( list4 ) );
        }

        return tweetRequestDto;
    }
}
