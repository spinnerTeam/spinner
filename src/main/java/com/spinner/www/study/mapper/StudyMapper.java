package com.spinner.www.study.mapper;

import com.spinner.www.study.dto.StudyCreateDto;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.io.StudyCreateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudyMapper {
    StudyCreateDto toStudyCreateDto(StudyCreateRequest studyCreateRequest);

}
