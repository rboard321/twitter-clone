package com.cooksys.twitter.entities;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {
	
	private String firstName;
	
	private String lastName;
	
	private String phone;
	
	private String email;
	
}
