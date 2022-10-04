package com.cooksys.twitter.services;

import java.util.List;
import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;

public interface UserService {

	List<UserResponseDto> getAllUsers();

	List<TweetResponseDto> getUserFeed(String username);

	List<TweetResponseDto> getUserMentions(String username);

	List<UserResponseDto> getUserFollowers(String username);

	List<UserResponseDto> getUserFollowing(String username);

	UserResponseDto deleteUser(CredentialsDto credentialsRequestDto);

	void followUser(String username, CredentialsDto credentialsRequestDto);

	void unFollowUser(String username, CredentialsDto credentialsDto);

	UserResponseDto getUserByUsername(String username);

	UserResponseDto createNewUser(UserRequestDto userRequestDto);

	List<TweetResponseDto> getUserTweets(String username);

	UserResponseDto updateUser(UserRequestDto userRequestDto);
}