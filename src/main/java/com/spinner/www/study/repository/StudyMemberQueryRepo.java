package com.spinner.www.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.member.entity.QMember;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.QStudyJoinMemberDto;
import com.spinner.www.study.dto.StudyJoinMemberDto;
import com.spinner.www.study.entity.QStudy;
import com.spinner.www.study.entity.QStudyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudyMemberQueryRepo {

    private final JPAQueryFactory jpaQueryFactory;

    public Long CountStudyMembers(Long studyIdx){
        QStudy qStudy = QStudy.study;
        QStudyMember qStudyMember = QStudyMember.studyMember;

        return jpaQueryFactory.select(
                qStudyMember.studyMemberIdx.count()
        )
                .from(qStudyMember)
                .join(qStudyMember.study, qStudy)
                .where(
                        qStudy.studyIdx.eq(studyIdx),
                        qStudy.studyIsRemoved.eq(false),
                        qStudyMember.isStudyMemberRemoved.eq(false),
                        qStudyMember.studyMemberStatus.eq(StudyMemberStatus.APPROVED)
                ).groupBy(qStudy.studyIdx)
                .fetchOne();
    }

    public List<StudyJoinMemberDto> findStudyMembersByStudyIdx(Long studyIdx){
        QMember qMember = QMember.member;
        QStudyMember qStudyMember = QStudyMember.studyMember;

        return jpaQueryFactory.select(new QStudyJoinMemberDto(
                qMember.memberNickname,
                qStudyMember.StudyMemberJoinInfo
        ))
                .from(qStudyMember)
                .join(qStudyMember.member, qMember)
                .where(
                        qStudyMember.study.studyIdx.eq(studyIdx),
                        qStudyMember.isStudyMemberRemoved.eq(false),
                        qStudyMember.studyMemberStatus.eq(StudyMemberStatus.APPROVED)
                        ).fetch();
    }
}
