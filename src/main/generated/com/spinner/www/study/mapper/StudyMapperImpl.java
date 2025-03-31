package com.spinner.www.study.mapper;

import com.spinner.www.study.dto.StudyCreateDto;
import com.spinner.www.study.dto.StudySearchParamDto;
import com.spinner.www.study.dto.StudyUpdateDto;
import com.spinner.www.study.io.StudyCreateRequest;
import com.spinner.www.study.io.StudySearchParamRequest;
import com.spinner.www.study.io.StudyUpdateRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:03+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class StudyMapperImpl implements StudyMapper {

    @Override
    public StudyCreateDto toStudyCreateDto(StudyCreateRequest studyCreateRequest) {
        if ( studyCreateRequest == null ) {
            return null;
        }

        StudyCreateDto.StudyCreateDtoBuilder studyCreateDto = StudyCreateDto.builder();

        studyCreateDto.studyName( studyCreateRequest.getStudyName() );
        studyCreateDto.studyIntro( studyCreateRequest.getStudyIntro() );
        studyCreateDto.studyCategoryType( studyCreateRequest.getStudyCategoryType() );
        studyCreateDto.studyMaxPeople( studyCreateRequest.getStudyMaxPeople() );

        return studyCreateDto.build();
    }

    @Override
    public StudyUpdateDto toStudyUpdateDto(StudyUpdateRequest studyUpdateRequest) {
        if ( studyUpdateRequest == null ) {
            return null;
        }

        StudyUpdateDto.StudyUpdateDtoBuilder studyUpdateDto = StudyUpdateDto.builder();

        studyUpdateDto.studyName( studyUpdateRequest.getStudyName() );
        studyUpdateDto.studyIntro( studyUpdateRequest.getStudyIntro() );
        studyUpdateDto.studyCategoryType( studyUpdateRequest.getStudyCategoryType() );
        studyUpdateDto.studyMaxPeople( studyUpdateRequest.getStudyMaxPeople() );

        return studyUpdateDto.build();
    }

    @Override
    public StudySearchParamDto toStudySearchParamDto(StudySearchParamRequest studySearchParamRequest) {
        if ( studySearchParamRequest == null ) {
            return null;
        }

        StudySearchParamDto.StudySearchParamDtoBuilder studySearchParamDto = StudySearchParamDto.builder();

        studySearchParamDto.keyword( studySearchParamRequest.getKeyword() );
        studySearchParamDto.sortType( studySearchParamRequest.getSortType() );

        return studySearchParamDto.build();
    }
}
