package com.spinner.www.study.mapper;

import com.spinner.www.study.dto.StudyMemberJoinDto;
import com.spinner.www.study.io.StudyMemberJoinRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:02+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class StudyMemberMapperImpl implements StudyMemberMapper {

    @Override
    public StudyMemberJoinDto toStudyMemberJoinDto(StudyMemberJoinRequest studyMemberJoinRequest) {
        if ( studyMemberJoinRequest == null ) {
            return null;
        }

        StudyMemberJoinDto.StudyMemberJoinDtoBuilder studyMemberJoinDto = StudyMemberJoinDto.builder();

        studyMemberJoinDto.studyMemberJoinIntro( studyMemberJoinRequest.getStudyMemberJoinIntro() );

        return studyMemberJoinDto.build();
    }
}
