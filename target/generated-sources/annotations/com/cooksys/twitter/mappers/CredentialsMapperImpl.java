package com.cooksys.twitter.mappers;

import com.cooksys.twitter.dtos.CredentialsDto;
import com.cooksys.twitter.entities.Credentials;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-22T12:33:13-0400",
    comments = "version: 1.4.1.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20220719-0747, environment: Java 17.0.4 (Eclipse Adoptium)"
)
@Component
public class CredentialsMapperImpl implements CredentialsMapper {

    @Override
    public CredentialsDto entityToDto(Credentials entity) {
        if ( entity == null ) {
            return null;
        }

        CredentialsDto credentialsDto = new CredentialsDto();

        credentialsDto.setPassword( entity.getPassword() );
        credentialsDto.setUsername( entity.getUsername() );

        return credentialsDto;
    }

    @Override
    public Credentials DtoToEntity(CredentialsDto credentialsRequestDto) {
        if ( credentialsRequestDto == null ) {
            return null;
        }

        Credentials credentials = new Credentials();

        credentials.setPassword( credentialsRequestDto.getPassword() );
        credentials.setUsername( credentialsRequestDto.getUsername() );

        return credentials;
    }
}
