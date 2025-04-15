package com.spinner.www.study.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberRole;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.StudyMemgberCreateDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Table(name = "study_member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @JoinColumn(name = "studyMemberStudyIdx", nullable = false)
    @Comment("스터디")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyMemberMemberIdx", nullable = false)
    @Comment("스터디 멤버")
    private Member member;

    @Comment("스터디 탈퇴 신청일")
    private LocalDate studyMemberWithdrawalDate;

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

    // 스터디원 상태 변경
    public void updateJoinStudyMember(StudyMemberStatus studyMemberStatus){
        this.studyMemberStatus = studyMemberStatus;
    }

    public void withdraw(){
        this.isStudyMemberRemoved = true;
        this.studyMemberWithdrawalDate = LocalDate.now();
    }
}
