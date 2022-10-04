package com.cooksys.twitter.controllers;


import java.util.List;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.cooksys.twitter.entities.Credentials;
import com.cooksys.twitter.entities.Profile;

import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

	private final UserService userService;

	@GetMapping
	public List<UserResponseDto> getAllUsers(){
		return userService.getAllUsers();
	}

	@GetMapping("/@{username}")
	public UserResponseDto getUserByUsername(@PathVariable String username, @RequestBody UserRequestDto userRequestDto){
		return userService.getUserByUsername(username, userRequestDto);
	}

	@GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getUserFeed(@PathVariable String username){
		return userService.getUserFeed(username);
	}

	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getUserMentions(@PathVariable String username, @RequestBody UserRequestDto userRequestDto){
		return userService.getUserMentions(username, userRequestDto);
	}

	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getUserFollowers(@PathVariable String username, @RequestBody UserRequestDto userRequestDto){
		return userService.getUserFollowers(username, userRequestDto);
	}

	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getUserFollowing(@PathVariable String username, @RequestBody UserRequestDto userRequestDto){
		return userService.getUserFollowing(username, userRequestDto);
	}
	//pass reqbody
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createNewUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createNewUser(userRequestDto);
	}

	@PatchMapping("/@{username}")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto updateUser(@RequestBody UserRequestDto userRequestDto){
		return userService.updateUser(userRequestDto);
	}

	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@RequestBody CredentialsDto credentialsDto){
		return userService.deleteUser(credentialsDto);
	}

	@PostMapping("/@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.followUser(username, credentialsDto);
	}

	@PostMapping("/@{username}/unfollow")
	public void unFollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.unFollowUser(username, credentialsDto);
	}
}