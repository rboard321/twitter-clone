package com.cooksys.twitter.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.mappers.TweetMapper;
import com.cooksys.twitter.mappers.UserMapper;
import org.springframework.stereotype.Service;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import com.cooksys.twitter.exceptions.BadRequestException;
import com.cooksys.twitter.exceptions.NotFoundException;
import com.cooksys.twitter.repositories.UserRepository;
import com.cooksys.twitter.services.UserService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TweetMapper tweetMapper;

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> activeUsers = returnAllActiveUsers();
        System.out.println(userMapper.entitiesToDtos(activeUsers).toString());
        return userMapper.entitiesToDtos(activeUsers);
    }

    @Override
    public UserResponseDto createNewUser(UserRequestDto userRequestDto) {
        checkUserRequestCredentials(userRequestDto);

        if(userRequestDto.getProfile().getEmail() == null) {
            throw new BadRequestException("Sorry this E-mail is blank");
        }

        Optional<User> optionalUser = userRepository.findByCredentials_Username(userRequestDto.getCredentials().getUsername());
        User user;

        if(optionalUser.isPresent()) {
            user = optionalUser.get();
            if(user.isDeleted()) {
                user.setDeleted(false);
            } else {
                throw new BadRequestException("Error: User " + user.getCredentials().getUsername() + " already exists.");
                //userRepository.saveAndFlush(user);
                //return userMapper.entityToDto(user);
            }

        } else {
            createUser(userRequestDto);
            user = userMapper.requestDtoToEntity(userRequestDto);

        }
        userRepository.saveAndFlush(user);
        return userMapper.entityToDto(user);

    }

    @Override
    public List<TweetResponseDto> getUserTweets(String username) {
        // Username check to make sure user exists
        checkUserValid(username);

        // Get user
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        User user = optionalUser.get();

        // Get user tweets
        List<Tweet> userTweets = user.getTweets();

        setTweetAuthorUsername(userTweets);

        // Sort in reverse-chronological order
        userTweets.sort(Comparator.comparing(Tweet::getPosted).reversed());

        // Remove deleted tweets
        userTweets.removeIf(Tweet::isDeleted);

        return tweetMapper.entitiesToDtos(userTweets);

    }

    private void checkUserRequestCredentials(UserRequestDto userRequestDto) {
        if(userRequestDto.getCredentials() == null || userRequestDto.getProfile() == null)
            throw new BadRequestException("Sorry this userername or password are blank");

        if(userRequestDto.getCredentials().getUsername() == null || userRequestDto.getCredentials().getUsername().isEmpty()
                || userRequestDto.getCredentials().getPassword() == null || userRequestDto.getCredentials().getPassword().isEmpty()) {
            throw new BadRequestException("Sorry this userername or password are blank");
        }
    }

    @Override
    public List<TweetResponseDto> getUserFeed(String username) {
        checkUserValid(username);
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        User user = optionalUser.get();
        List<Tweet> feed = user.getTweets();

        for(User userNext: user.getFollowing()) {
            feed.addAll(userNext.getTweets());
        }

        setTweetAuthorUsername(feed);

        feed.removeIf(Tweet::isDeleted);
        feed.sort(Comparator.comparing(Tweet::getPosted).reversed());
        return userMapper.entityToDtoTweets(feed);
    }

    @Override
    public List<TweetResponseDto> getUserMentions(String username) {
        checkUserValid(username);
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        User user = optionalUser.get();
        List<Tweet> mentions = user.getMentions();

        setTweetAuthorUsername(mentions);

        mentions.removeIf(Tweet::isDeleted);
        mentions.sort(Comparator.comparing(Tweet::getPosted).reversed());
        return userMapper.entityToDtoTweets(mentions);
    }

    @Override
    public List<UserResponseDto> getUserFollowers(String username) {

        checkUserValid(username);
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        User user = optionalUser.get();
        List<User> followers = user.getFollowers();

        followers.removeIf(User::isDeleted);

        return userMapper.entitiesToDtos(followers);
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        checkUserValid(username);
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        return userMapper.entityToDto(optionalUser.get());

    }

    @Override
    public List<UserResponseDto> getUserFollowing(String username) {
        checkUserValid(username);
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        User user = optionalUser.get();
        List<User> following = user.getFollowing();

        following.removeIf(User::isDeleted);

        return userMapper.entitiesToDtos(following);
    }

    @Override
    public UserResponseDto updateUser(UserRequestDto userRequestDto) {
        checkUserValid(userRequestDto);

        Optional<User> optionalUser = userRepository.findByCredentials_Username(userRequestDto.getCredentials().getUsername());
        User user = optionalUser.get();

        if(userRequestDto.getProfile() == null) {
            throw new BadRequestException("Error: Request body must contain a profile.");
        } else {
            if(userRequestDto.getProfile().getEmail() == null)
                return userMapper.entityToDto(user);
            user.setCredentials(userRequestDto.getCredentials());
            user.setProfile(userRequestDto.getProfile());
        }
        userRepository.saveAndFlush(user);

        return userMapper.entityToDto(user);
    }

    @Override
    public UserResponseDto deleteUser(CredentialsDto credentialsDto) {
        checkUserValid(credentialsDto.getUsername());
        Optional<User> optionalUser = userRepository.findByCredentials_Username(credentialsDto.getUsername());
        if(optionalUser.isEmpty() || optionalUser.get().isDeleted()) {
            throw new BadRequestException("Sorry this user does not exist");
        }
        User user = optionalUser.get();

        user.setDeleted(true);
        userRepository.saveAndFlush(user);


        return userMapper.entityToDto(user);
    }

    @Override
    public void followUser(String username, CredentialsDto credentialsDto) {

        checkUserValid(username);
        checkCredentials(credentialsDto);
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        User userToFollow = optionalUser.get();
        optionalUser = userRepository.findByCredentials_Username(credentialsDto.getUsername());
        User user = optionalUser.get();
        if (userToFollow.getFollowers().contains(user))
            throw new BadRequestException("Error: User already following " + userToFollow.getCredentials().getUsername() + ".");
        user.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(user);
        userRepository.saveAndFlush(user);
        userRepository.saveAndFlush(userToFollow);
    }

    @Override
    public void unFollowUser(String username, CredentialsDto credentialsDto) {
        checkUserValid(username);
        checkCredentials(credentialsDto);
        if(credentialsDto == null) {
            throw new BadRequestException("Sorry you do not have the right credentials");
        }
        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        User userToUnfollow = optionalUser.get();
        optionalUser = userRepository.findByCredentials_Username(credentialsDto.getUsername());
        User user = optionalUser.get();
        if (userToUnfollow.getFollowers().contains(user)) {
            user.getFollowing().remove(userToUnfollow);
            userToUnfollow.getFollowers().remove(userToUnfollow);
        }else{
            throw new BadRequestException("Error! User not following " + userToUnfollow.getCredentials().getUsername());
        }
        userRepository.saveAndFlush(user);
        userRepository.saveAndFlush(userToUnfollow);

    }

//	Helper methods below


    private List<User> returnAllActiveUsers() {
        List<User> users = userRepository.findAll();
        users.removeIf(User::isDeleted);
        return users;
    }

    private void checkCredentials(CredentialsDto credentialsDto) {
        if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
            throw new BadRequestException("Request body must contain valid credentials.");
        }
    }

    private void checkUserValid(UserRequestDto userRequestDto) {
        if(userRequestDto.getCredentials() == null || userRequestDto.getCredentials().getUsername() == null
                || userRequestDto.getCredentials().getPassword() == null) {
            throw new BadRequestException("We need a valid uername and password.");
        }
        Optional<User> optionalUser = userRepository.findByCredentials_Username(userRequestDto.getCredentials().getUsername());
        if (optionalUser.isEmpty()  || optionalUser.get().isDeleted()) {
            throw new BadRequestException("Sorry this is not a valid user.");
        }
    }

    private void checkUserValid(String username) {

        Optional<User> optionalUser = userRepository.findByCredentials_Username(username);
        if (optionalUser.isEmpty()  || optionalUser.get().isDeleted()) {
            throw new BadRequestException("Sorry this is not a valid user.");
        }
    }

    private void checkProfile(UserRequestDto userRequestDto) {

        if(userRequestDto.getProfile() == null) {
            throw new BadRequestException("Sorry no profile exists.");
        }
    }


    private void runUserExistsTests(User user){
        if (user == null)
            throw new NotFoundException("Error! Username or password invalid.");
        if (user.isDeleted())
            throw new NotFoundException("Error! User has been deleted.");
    }

    private void createUser(UserRequestDto userRequestDto) {

        userRequestDto.setDeleted(false);
        userRequestDto.setProfile(userRequestDto.getProfile());
        userRequestDto.setCredentials(userRequestDto.getCredentials());
        userRequestDto.setFollowers(new ArrayList<>());
        userRequestDto.setFollowing(new ArrayList<>());
        userRequestDto.setLikedTweets(new ArrayList<>());
        userRequestDto.setMentions(new ArrayList<>());
        userRequestDto.setTweets(new ArrayList<>());
    }

    private void setTweetAuthorUsername(List<Tweet> tweetList){
        for (Tweet t : tweetList){
            t.getAuthor().setUsername(t.getAuthor().getCredentials().getUsername());
        }
    }


}