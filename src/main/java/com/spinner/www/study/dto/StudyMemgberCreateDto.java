package com.spinner.www.study.dto;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberRole;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMemgberCreateDto {

    private StudyMemberRole studyMemberRole;
    private StudyMemberStatus studyMemberStatus;
    private boolean isStudyMemberRemoved;
    private String StudyMemberJoinInfo;
    private Study study;
    private Member member;
}
