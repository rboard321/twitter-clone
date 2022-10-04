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
    date = "2022-09-22T12:33:13-0400",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
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
        userResponseDto.setJoined( user.getJoined() );
        userResponseDto.setProfile( profileToProfileDto( user.getProfile() ) );

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
        user.setDeleted( userRequestDto.isDeleted() );
        List<User> list = userRequestDto.getFollowers();
        if ( list != null ) {
            user.setFollowers( new ArrayList<User>( list ) );
        }
        List<User> list1 = userRequestDto.getFollowing();
        if ( list1 != null ) {
            user.setFollowing( new ArrayList<User>( list1 ) );
        }
        user.setJoined( userRequestDto.getJoined() );
        List<Tweet> list2 = userRequestDto.getLikedTweets();
        if ( list2 != null ) {
            user.setLikedTweets( new ArrayList<Tweet>( list2 ) );
        }
        List<Tweet> list3 = userRequestDto.getMentions();
        if ( list3 != null ) {
            user.setMentions( new ArrayList<Tweet>( list3 ) );
        }
        user.setProfile( userRequestDto.getProfile() );
        List<Tweet> list4 = userRequestDto.getTweets();
        if ( list4 != null ) {
            user.setTweets( new ArrayList<Tweet>( list4 ) );
        }

        return user;
    }

    @Override
    public User requestDtoToEntity(UserRequestDto userRequestDto) {
        if ( userRequestDto == null ) {
            return null;
        }

        User user = new User();

        user.setCredentials( userRequestDto.getCredentials() );
        user.setDeleted( userRequestDto.isDeleted() );
        List<User> list = userRequestDto.getFollowers();
        if ( list != null ) {
            user.setFollowers( new ArrayList<User>( list ) );
        }
        List<User> list1 = userRequestDto.getFollowing();
        if ( list1 != null ) {
            user.setFollowing( new ArrayList<User>( list1 ) );
        }
        user.setJoined( userRequestDto.getJoined() );
        List<Tweet> list2 = userRequestDto.getLikedTweets();
        if ( list2 != null ) {
            user.setLikedTweets( new ArrayList<Tweet>( list2 ) );
        }
        List<Tweet> list3 = userRequestDto.getMentions();
        if ( list3 != null ) {
            user.setMentions( new ArrayList<Tweet>( list3 ) );
        }
        user.setProfile( userRequestDto.getProfile() );
        List<Tweet> list4 = userRequestDto.getTweets();
        if ( list4 != null ) {
            user.setTweets( new ArrayList<Tweet>( list4 ) );
        }

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

        profileDto.setEmail( profile.getEmail() );
        profileDto.setFirstName( profile.getFirstName() );
        profileDto.setLastName( profile.getLastName() );
        profileDto.setPhone( profile.getPhone() );

        return profileDto;
    }

    protected TweetResponseDto tweetToTweetResponseDto(Tweet tweet) {
        if ( tweet == null ) {
            return null;
        }

        TweetResponseDto tweetResponseDto = new TweetResponseDto();

        tweetResponseDto.setAuthor( tweet.getAuthor() );
        tweetResponseDto.setContent( tweet.getContent() );
        tweetResponseDto.setDeleted( tweet.isDeleted() );
        List<Hashtag> list = tweet.getHashtags();
        if ( list != null ) {
            tweetResponseDto.setHashtags( new ArrayList<Hashtag>( list ) );
        }
        tweetResponseDto.setId( tweet.getId() );
        tweetResponseDto.setInReplyTo( tweet.getInReplyTo() );
        List<User> list1 = tweet.getLikes();
        if ( list1 != null ) {
            tweetResponseDto.setLikes( new ArrayList<User>( list1 ) );
        }
        tweetResponseDto.setPosted( tweet.getPosted() );
        List<Tweet> list2 = tweet.getReplies();
        if ( list2 != null ) {
            tweetResponseDto.setReplies( new ArrayList<Tweet>( list2 ) );
        }
        tweetResponseDto.setRepostOf( tweet.getRepostOf() );
        List<Tweet> list3 = tweet.getReposts();
        if ( list3 != null ) {
            tweetResponseDto.setReposts( new ArrayList<Tweet>( list3 ) );
        }
        List<User> list4 = tweet.getUserMentioned();
        if ( list4 != null ) {
            tweetResponseDto.setUserMentioned( new ArrayList<User>( list4 ) );
        }

        return tweetResponseDto;
    }
}
