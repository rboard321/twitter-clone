package com.cooksys.twitter.dtos;

import java.sql.Timestamp;
import java.util.List;


import javax.persistence.Embedded;


import org.hibernate.annotations.CreationTimestamp;

import com.cooksys.twitter.entities.Credentials;
import com.cooksys.twitter.entities.Profile;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
	
	@Embedded
	private Credentials credentials;
	
	@Embedded
	private Profile profile;
	
	@CreationTimestamp
	private Timestamp joined;
	

	private List<Tweet> tweets;
	
	
	private List<Tweet> likedTweets;
	
	
	private List<User> followers;
	
	
	private List<User> following;
	
	
	private List<Tweet> mentions;


	private boolean deleted;
	
}
