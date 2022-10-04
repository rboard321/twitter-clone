package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.ProfileDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.entities.Credentials;
import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Profile;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-01T19:33:13-0400",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto entityToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUsername( userCredentialsUsername( user ) );
        userResponseDto.setProfile( profileToProfileDto( user.getProfile() ) );
        userResponseDto.setJoined( user.getJoined() );

        return userResponseDto;
    }

    @Override
    public List<UserResponseDto> entitiesToDtos(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( user.size() );
        for ( User user1 : user ) {
            list.add( entityToDto( user1 ) );
        }

        return list;
    }

    @Override
    public List<UserResponseDto> entityToDto(List<User> following) {
        if ( following == null ) {
            return null;
        }

        List<UserResponseDto> list = new ArrayList<UserResponseDto>( following.size() );
        for ( User user : following ) {
            list.add( entityToDto( user ) );
        }

        return list;
    }

    @Override
    public List<TweetResponseDto> entityToDtoMentions(List<Tweet> mentions) {
        if ( mentions == null ) {
            return null;
        }

        List<TweetResponseDto> list = new ArrayList<TweetResponseDto>( mentions.size() );
        for ( Tweet tweet : mentions ) {
            list.add( tweetToTweetResponseDto( tweet ) );
        }

        return list;
    }

    @Override
    public List<TweetResponseDto> entityToDtoTweets(List<Tweet> tweets) {
        if ( tweets == null ) {
            return null;
        }

        List<TweetResponseDto> list = new ArrayList<TweetResponseDto>( tweets.size() );
        for ( Tweet tweet : tweets ) {
            list.add( tweetToTweetResponseDto( tweet ) );
        }

        return list;
    }

    @Override
    public User entityToDto(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setCredentials( userRequestDto.getCredentials() );
        user.setProfile( userRequestDto.getProfile() );
        user.setJoined( userRequestDto.getJoined() );
        List<Tweet> list = userRequestDto.getTweets();
        if ( list != null ) {
            user.setTweets( new ArrayList<Tweet>( list ) );
        }
        List<Tweet> list1 = userRequestDto.getLikedTweets();
        if ( list1 != null ) {
            user.setLikedTweets( new ArrayList<Tweet>( list1 ) );
        }
        List<User> list2 = userRequestDto.getFollowers();
        if ( list2 != null ) {
            user.setFollowers( new ArrayList<User>( list2 ) );
        }
        List<User> list3 = userRequestDto.getFollowing();
        if ( list3 != null ) {
            user.setFollowing( new ArrayList<User>( list3 ) );
        }
        List<Tweet> list4 = userRequestDto.getMentions();
        if ( list4 != null ) {
            user.setMentions( new ArrayList<Tweet>( list4 ) );
        }
        user.setDeleted( userRequestDto.isDeleted() );

        return user;
    }

    @Override
    public User requestDtoToEntity(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setCredentials( userRequestDto.getCredentials() );
        user.setProfile( userRequestDto.getProfile() );
        user.setJoined( userRequestDto.getJoined() );
        List<Tweet> list = userRequestDto.getTweets();
        if ( list != null ) {
            user.setTweets( new ArrayList<Tweet>( list ) );
        }
        List<Tweet> list1 = userRequestDto.getLikedTweets();
        if ( list1 != null ) {
            user.setLikedTweets( new ArrayList<Tweet>( list1 ) );
        }
        List<User> list2 = userRequestDto.getFollowers();
        if ( list2 != null ) {
            user.setFollowers( new ArrayList<User>( list2 ) );
        }
        List<User> list3 = userRequestDto.getFollowing();
        if ( list3 != null ) {
            user.setFollowing( new ArrayList<User>( list3 ) );
        }
        List<Tweet> list4 = userRequestDto.getMentions();
        if ( list4 != null ) {
            user.setMentions( new ArrayList<Tweet>( list4 ) );
        }
        user.setDeleted( userRequestDto.isDeleted() );

        return user;
    }

    private String userCredentialsUsername(User user) {
        if ( user == null ) {
            return null;
        }
        Credentials credentials = user.getCredentials();
        if ( credentials == null ) {
            return null;
        }
        String username = credentials.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    protected ProfileDto profileToProfileDto(Profile profile) {
        if ( profile == null ) {
            return null;
        }

        ProfileDto profileDto = new ProfileDto();

        profileDto.setFirstName( profile.getFirstName() );
        profileDto.setLastName( profile.getLastName() );
        profileDto.setEmail( profile.getEmail() );
        profileDto.setPhone( profile.getPhone() );

        return profileDto;
    }

    protected TweetResponseDto tweetToTweetResponseDto(Tweet tweet) {
        if ( tweet == null ) {
            return null;
        }

        TweetResponseDto tweetResponseDto = new TweetResponseDto();

        tweetResponseDto.setId( tweet.getId() );
        tweetResponseDto.setAuthor( tweet.getAuthor() );
        tweetResponseDto.setDeleted( tweet.isDeleted() );
        tweetResponseDto.setPosted( tweet.getPosted() );
        tweetResponseDto.setContent( tweet.getContent() );
        tweetResponseDto.setInReplyTo( tweet.getInReplyTo() );
        tweetResponseDto.setRepostOf( tweet.getRepostOf() );
        List<Tweet> list = tweet.getReposts();
        if ( list != null ) {
            tweetResponseDto.setReposts( new ArrayList<Tweet>( list ) );
        }
        List<Tweet> list1 = tweet.getReplies();
        if ( list1 != null ) {
            tweetResponseDto.setReplies( new ArrayList<Tweet>( list1 ) );
        }
        List<User> list2 = tweet.getLikes();
        if ( list2 != null ) {
            tweetResponseDto.setLikes( new ArrayList<User>( list2 ) );
        }
        List<Hashtag> list3 = tweet.getHashtags();
        if ( list3 != null ) {
            tweetResponseDto.setHashtags( new ArrayList<Hashtag>( list3 ) );
        }
        List<User> list4 = tweet.getUserMentioned();
        if ( list4 != null ) {
            tweetResponseDto.setUserMentioned( new ArrayList<User>( list4 ) );
        }

        return tweetResponseDto;
    }
}
