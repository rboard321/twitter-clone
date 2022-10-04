package com.cooksys.twitter.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Embeddable
public class Credentials {
	
	@Column(unique=true)
	private String username;
	
	private String password;

}
