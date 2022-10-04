package com.cooksys.twitter.mappers;


import com.cooksys.twitter.dtos.TweetRequestDto;
import com.cooksys.twitter.dtos.TweetResponseDto;
import com.cooksys.twitter.entities.Tweet;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring", uses = { TweetMapper.class })
public interface TweetMapper {
	
    TweetResponseDto entityToDto(Tweet entity);

    List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

    Tweet requestDtoToEntity(TweetRequestDto questionRequestDto);

    Tweet responseDtoToEntity(TweetResponseDto questionResponseDto);

    TweetRequestDto entityToRequestDto(Tweet tweet);


}
