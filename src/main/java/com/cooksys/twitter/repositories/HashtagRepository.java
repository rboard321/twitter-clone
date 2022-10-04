package com.cooksys.twitter.repositories;

import com.cooksys.twitter.entities.Hashtag;
import com.cooksys.twitter.entities.Tweet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    List<Hashtag> findAllByLabel(String string);

    Hashtag getByLabel(String string);
}