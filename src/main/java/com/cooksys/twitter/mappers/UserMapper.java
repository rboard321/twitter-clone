package com.cooksys.twitter.mappers;


import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.dtos.UserRequestDto;
import com.cooksys.twitter.dtos.UserResponseDto;
import com.cooksys.twitter.entities.Tweet;
import com.cooksys.twitter.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface UserMapper {

	// since we use embeddables, we have to tell the mapping what to target during generation
	@Mapping(source = "credentials.username", target = "username")
	UserResponseDto entityToDto(User user);

	List<UserResponseDto> entitiesToDtos(List<User> user);

	List<UserResponseDto> entityToDto(List<User> following);

	List<TweetResponseDto> entityToDtoTweets(List<Tweet> tweets);

	User entityToDto(UserRequestDto userRequestDto);

	User requestDtoToEntity(UserRequestDto userRequestDto);
}
