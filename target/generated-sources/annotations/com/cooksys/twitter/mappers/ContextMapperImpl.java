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
    date = "2022-09-22T12:33:12-0400",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class ContextMapperImpl implements ContextMapper {

    @Override
    public ContextDto entityToDto(Context entity) {
        if ( entity == null ) {
            return null;
        }

        ContextDto contextDto = new ContextDto();

        List<Tweet> list = entity.getAfter();
        if ( list != null ) {
            contextDto.setAfter( new ArrayList<Tweet>( list ) );
        }
        List<Tweet> list1 = entity.getBefore();
        if ( list1 != null ) {
            contextDto.setBefore( new ArrayList<Tweet>( list1 ) );
        }
        contextDto.setTarget( entity.getTarget() );

        return contextDto;
    }
}
