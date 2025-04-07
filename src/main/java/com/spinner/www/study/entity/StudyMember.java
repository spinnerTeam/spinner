package com.spinner.www.study.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberRole;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.StudyMemgberCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "study_member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("스터디 멤버 테이블")
public class StudyMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("스터디 멤버 PK")
    private Long studyMemberIdx;
    
    @Enumerated(EnumType.STRING)
    @Comment("스터디 권한")
    private StudyMemberRole studyMemberRole;

    @Enumerated(EnumType.STRING)
    @Comment("스터디 가입 상태")
    private StudyMemberStatus studyMemberStatus;

    @Comment("탈퇴 여부")
    private boolean isStudyMemberRemoved;

    @Comment("가입 소개")
    private String StudyMemberJoinInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyIdx", nullable = false)
    @Comment("스터디")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx", nullable = false)
    @Comment("스터디 멤버")
    private Member member;

    public static StudyMember insertStudyMember(StudyMemgberCreateDto studyMemgberCreateDto){
        return StudyMember.builder()
                .studyMemberRole(studyMemgberCreateDto.getStudyMemberRole())
                .studyMemberStatus(studyMemgberCreateDto.getStudyMemberStatus())
                .isStudyMemberRemoved(false)
                .StudyMemberJoinInfo(studyMemgberCreateDto.getStudyMemberJoinInfo())
                .study(studyMemgberCreateDto.getStudy())
                .member(studyMemgberCreateDto.getMember())
                .build();
    }
}
