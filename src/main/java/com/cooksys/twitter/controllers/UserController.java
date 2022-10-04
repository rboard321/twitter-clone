package com.cooksys.twitter.controllers;

import java.util.List;
import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.cooksys.twitter.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@GetMapping
	public List<UserResponseDto> getAllUsers(){
		return userService.getAllUsers();
	}

	@GetMapping("/@{username}")
	public UserResponseDto getUserByUsername(@PathVariable String username){
		return userService.getUserByUsername(username);
	}

	@GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getUserFeed(@PathVariable String username){
		return userService.getUserFeed(username);
	}

	@GetMapping("/@{username}/tweets")
	public List<TweetResponseDto> getUserTweets(@PathVariable String username) {
		return userService.getUserTweets(username);
	}

	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getUserMentions(@PathVariable String username){
		return userService.getUserMentions(username);
	}

	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getUserFollowers(@PathVariable String username){
		return userService.getUserFollowers(username);
	}

	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getUserFollowing(@PathVariable String username){
		return userService.getUserFollowing(username);
	}
	//pass reqbody======================================================================
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto createNewUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createNewUser(userRequestDto);
	}

	@PostMapping("/@{username}/follow")
	public void followUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.followUser(username, credentialsDto);
	}

	@PostMapping("/@{username}/unfollow")
	@ResponseStatus(HttpStatus.OK)
	public void unFollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		userService.unFollowUser(username, credentialsDto);
	}

	@PatchMapping("/@{username}")
	public UserResponseDto updateUser(@RequestBody UserRequestDto userRequestDto){
		return userService.updateUser(userRequestDto);
	}

	//Delete reqbody======================================================================
	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@RequestBody CredentialsDto credentialsDto){
		return userService.deleteUser(credentialsDto);
	}
}