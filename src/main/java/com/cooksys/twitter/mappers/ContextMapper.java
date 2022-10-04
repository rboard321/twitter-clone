package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.ContextDto;
import com.cooksys.twitter.entities.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { ContextMapper.class })
public interface ContextMapper {
    ContextDto entityToDto(Context entity);
}
