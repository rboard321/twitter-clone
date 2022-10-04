package com.cooksys.twitter.mappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ContextMapper.class })
public interface ProfileMapper {
}
