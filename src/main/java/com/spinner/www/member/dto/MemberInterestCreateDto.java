package com.spinner.www.member.dto;

import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberInterestCreateDto {

    private StudyTopic studyTopic;
    private Member member;

    public MemberInterestCreateDto(StudyTopic studyTopic, Member member){
        this.studyTopic = studyTopic;
        this.member = member;
    }
}
