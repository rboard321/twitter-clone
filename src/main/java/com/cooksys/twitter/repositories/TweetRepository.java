package com.cooksys.twitter.repositories;

import com.cooksys.twitter.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    // TO-DO: ADD SOME DERIVED QUERIES

}
