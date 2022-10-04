package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.entities.Credentials;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { TweetMapper.class })
public interface CredentialsMapper {

    CredentialsDto entityToDto(Credentials entity);

    Credentials DtoToEntity(CredentialsDto credentialsRequestDto);

}
