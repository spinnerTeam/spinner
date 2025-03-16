package com.spinner.www.study.mapper;

import com.spinner.www.study.dto.StudyMemberJoinDto;
import com.spinner.www.study.io.StudyMemberJoinRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudyMemberMapper {
    StudyMemberJoinDto toStudyMemberJoinDto(StudyMemberJoinRequest studyMemberJoinRequest);
}
