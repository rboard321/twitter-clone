package com.cooksys.twitter.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Tweet {

	@Id
	@GeneratedValue
	private Long id;

	
	@ManyToOne
	private User author;
	
	@CreationTimestamp
	private Timestamp posted;
	
	private boolean deleted;
	
	private String content;
	
	@ManyToOne
	private Tweet inReplyTo;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Tweet repostOf;


	@JsonIgnore
	@OneToMany(mappedBy = "repostOf", cascade = CascadeType.ALL)
	private List<Tweet> reposts;

	@JsonIgnore
	@OneToMany(mappedBy = "inReplyTo", cascade = CascadeType.ALL)

	private List<Tweet> replies;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH)
	private List<User> likes;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE)
	private List<Hashtag> hashtags;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH)
	private List<User> userMentioned;
}
