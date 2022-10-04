package com.cooksys.twitter.dtos;

import com.cooksys.twitter.entities.Tweet;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data

public class HashtagDto {

    private Long id;

    private String label;

    private Timestamp firstUsed;

    private Timestamp lastUsed;

    private List<Tweet> tweets;
}
