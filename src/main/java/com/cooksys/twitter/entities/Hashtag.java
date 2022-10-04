package com.cooksys.twitter.entities;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Hashtag {
	
	@Id
	@GeneratedValue
	private Long id;
	@Column
	
	private String label;
	
	@CreationTimestamp
	private Timestamp firstUsed;


	@UpdateTimestamp
	private Timestamp lastUsed;

	@JsonIgnore
	@ManyToMany(mappedBy = "hashtags")
	private List<Tweet> tweets;

}
