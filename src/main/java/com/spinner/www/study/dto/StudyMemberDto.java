package com.spinner.www.study.dto;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberRole;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.entity.Study;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StudyMemberDto {

    private Long studyMemberIdx;
    private StudyMemberRole studyMemberRole;
    private StudyMemberStatus studyMemberStatus;
    private boolean isStudyMemberRemoved;
    private String StudyMemberJoinInfo;
    private Study study;
    private Member member;

}
