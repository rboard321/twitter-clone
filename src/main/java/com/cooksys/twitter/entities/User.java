package com.cooksys.twitter.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import lombok.NoArgsConstructor;
@Table(name = "user_table")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue
	private Long id;
	
	@Embedded
	private Credentials credentials;
	
	@Embedded
	private Profile profile;
	
	@CreationTimestamp
	private Timestamp joined;

	@JsonIgnore
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Tweet> tweets;

	@JsonIgnore
	@ManyToMany(mappedBy = "likes")
	private List<Tweet> likedTweets;

	@JsonIgnore
	@ManyToMany(mappedBy = "following")
	private List<User> followers;

	@JsonIgnore
	@ManyToMany
	private List<User> following;

	@JsonIgnore
	@ManyToMany(mappedBy = "userMentioned")
	private List<Tweet> mentions;

	@Column
	private boolean deleted;
	
	@Transient
	private String username;
}
