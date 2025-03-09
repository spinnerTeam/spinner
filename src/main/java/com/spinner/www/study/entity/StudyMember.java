package com.spinner.www.study.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberRoleType;
import com.spinner.www.study.constants.StudyMemberStatusType;
import com.spinner.www.study.dto.StudyMemberJoinDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyMember extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studyMemberIdx")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyIdx")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Comment("스터디 상태 타입 | waiting (대기), join (가입)")
    private StudyMemberStatusType studyMemberStatus;

    @Enumerated(EnumType.STRING)
    @Comment("스터디 롤 타입 | leader (스터디장), member (스터디원)")
    private StudyMemberRoleType studyMemberRole;

    @Comment("삭제 여부")
    private String studyMemberRemoved;

    @Comment("가입 신청 소개")
    private String studyMemberJoinIntro;

    // 연관관계 메서드
    public void addToStudy(Study study) {
        if (this.study == null) {
            this.study = study;
        }
    }

    // 스터디 생성 시 스터디장
    public static StudyMember createStudyMemberLeader(Study study, Member member) {
        return StudyMember
            .builder()
            .study(study)
            .member(member)
            .studyMemberRole(StudyMemberRoleType.leader)
            .studyMemberRemoved("N")
            .studyMemberJoinIntro("스터디장")
            .studyMemberStatus(StudyMemberStatusType.JOIN)
            .build();
    }

    // 가입 신청
    public static void createStudyMember(Study study, Member member, StudyMemberJoinDto studyMemberJoinDto) {
        StudyMember
            .builder()
            .study(study)
            .member(member)
            .studyMemberRole(StudyMemberRoleType.member)
            .studyMemberRemoved("N")
            .studyMemberJoinIntro(studyMemberJoinDto.getStudyMemberJoinIntro())
            .studyMemberStatus(StudyMemberStatusType.WAITING)
            .build();
    }

    // 가입 승인
    public void acceptStudyMember() {
        this.studyMemberStatus = StudyMemberStatusType.JOIN;
    }

    // 재가입 요청 가능 승인
    public void disapproveStudyMember() {
        this.studyMemberStatus = StudyMemberStatusType.DISAPPROVE;
    }

    // 멤버 강퇴
    public void kickStudyMember() {
        this.studyMemberStatus = StudyMemberStatusType.KICK;
        this.studyMemberRemoved = "Y";
    }

    // 멤버 탈퇴
    public void leaveStudyMember() {
        this.studyMemberRemoved = "Y";
    }
}
