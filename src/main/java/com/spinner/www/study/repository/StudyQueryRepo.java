package com.spinner.www.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.QMember;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.MyStudyListDto;
import com.spinner.www.study.dto.PendingStudyMemberDto;
import com.spinner.www.study.dto.QMyStudyListDto;
import com.spinner.www.study.dto.QPendingStudyMemberDto;
import com.spinner.www.study.entity.QStudy;
import com.spinner.www.study.entity.QStudyMember;
import com.spinner.www.study.entity.StudyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudyQueryRepo {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 나의 스터디 (참여스터디, 가입대기스터디, 종료 스터디)
     * @param studyMemberStatus StudyMemberStatus
     * @param member Member
     * @return List<MyStudyListDto>
     */
    public List<MyStudyListDto> joinedStudy(StudyMemberStatus studyMemberStatus, Member member){

        QStudyMember studyMember = QStudyMember.studyMember;
        QStudy study = QStudy.study;
        return jpaQueryFactory.select(new QMyStudyListDto(
                study.studyName,
                study.studyInfo,
                studyMember.studyMemberRole.stringValue()
        )).from(studyMember)
                .join(studyMember.study , study)
                .where(
                        studyMember.member.memberIdx.eq(member.getMemberIdx()),
                        studyMember.studyMemberStatus.eq(studyMemberStatus),
                        studyMember.isStudyMemberRemoved.isFalse()
                ).fetch();
    }

    /**
     * 스터디 멤버관리 (신청인원, 참여인원)
     * @param studyIdx Long
     * @param studyStatus String
     * @return List<PendingStudyMemberDto>
     */
    public List<PendingStudyMemberDto> pendingStudyMember(Long studyIdx, String studyStatus){
        QStudy qStudy = QStudy.study;
        QStudyMember qStudyMember = QStudyMember.studyMember;
        QMember qMember = QMember.member;

        return jpaQueryFactory.select(new QPendingStudyMemberDto(
                        qMember.memberIdx,
                        qMember.memberNickname
                ))
                .from(qStudyMember)
                .join(qStudyMember.study, qStudy)
                .join(qStudyMember.member, qMember)
                .where(
                        qStudy.studyIdx.eq(studyIdx),
                        qStudyMember.studyMemberStatus.stringValue().eq(studyStatus)
                ).fetch();

    }
}
