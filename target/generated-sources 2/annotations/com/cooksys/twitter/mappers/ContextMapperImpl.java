package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.ContextDto;
import com.cooksys.twitter.entities.Context;
import com.cooksys.twitter.entities.Tweet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-01T20:44:32-0400",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 11.0.16 (Amazon.com Inc.)"
)
@Component
public class ContextMapperImpl implements ContextMapper {

    @Override
    public ContextDto entityToDto(Context entity) {
        if ( entity == null ) {
            return null;
        }

        ContextDto contextDto = new ContextDto();

        contextDto.setTarget( entity.getTarget() );
        List<Tweet> list = entity.getBefore();
        if ( list != null ) {
            contextDto.setBefore( new ArrayList<Tweet>( list ) );
        }
        List<Tweet> list1 = entity.getAfter();
        if ( list1 != null ) {
            contextDto.setAfter( new ArrayList<Tweet>( list1 ) );
        }

        return contextDto;
    }

    @Override
    public Context DtoToEntity(ContextDto contextDto) {
        if ( contextDto == null ) {
            return null;
        }

        Context context = new Context();

        context.setTarget( contextDto.getTarget() );
        List<Tweet> list = contextDto.getBefore();
        if ( list != null ) {
            context.setBefore( new ArrayList<Tweet>( list ) );
        }
        List<Tweet> list1 = contextDto.getAfter();
        if ( list1 != null ) {
            context.setAfter( new ArrayList<Tweet>( list1 ) );
        }

        return context;
    }
}
