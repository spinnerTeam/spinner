package com.spinner.www.vote.mapper;

import com.spinner.www.vote.dto.VoteSelectDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:03+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class VoteMapperImpl implements VoteMapper {

    @Override
    public VoteSelectDto toVoteSelectDto(Long boardIdx) {
        if ( boardIdx == null ) {
            return null;
        }

        VoteSelectDto.VoteSelectDtoBuilder voteSelectDto = VoteSelectDto.builder();

        voteSelectDto.boardIdx( boardIdx );

        return voteSelectDto.build();
    }
}
