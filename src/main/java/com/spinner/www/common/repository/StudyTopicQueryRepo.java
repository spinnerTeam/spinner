package com.spinner.www.common.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.common.entity.QCommonCode;
import com.spinner.www.common.entity.QStudyTopic;
import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.member.entity.QMember;
import com.spinner.www.member.entity.QMemberInterest;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.QStudyListDto;
import com.spinner.www.study.dto.StudyListDto;
import com.spinner.www.study.entity.QStudy;
import com.spinner.www.study.entity.QStudyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudyTopicQueryRepo {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 스터디 주제 조회
     * @param depth int
     * @param parentIdx Long
     * @return List<StudyTopic>
     */
    public List<StudyTopic> findWithCommonCode(int depth, Long parentIdx){
        QStudyTopic studyTopic = QStudyTopic.studyTopic;
        QCommonCode commonCode = QCommonCode.commonCode;

        return jpaQueryFactory
                .selectFrom(studyTopic)
                .join(studyTopic.commonCode, commonCode).fetchJoin()
                .where(
                        studyTopic.studyTopicDepth.eq(depth),
                        studyTopic.studyTopicParentIdx.eq(parentIdx)
                ).fetch();
    }

    /**
     * 멤버 1명의 관심 분야
     * @param memberIdx
     * @return List<Long> Long
     */
    public List<Long> findInterestCodeIdxByMember(Long memberIdx){
        QMember qMember = QMember.member;
        QMemberInterest qMemberInterest = QMemberInterest.memberInterest;
        QStudyTopic qStudyTopic = QStudyTopic.studyTopic;
        QCommonCode qCommonCode = QCommonCode.commonCode;

        return jpaQueryFactory.
                select(qCommonCode.codeIdx)
                .from(qMemberInterest)
                .join(qMemberInterest.member, qMember)
                .join(qMemberInterest.studyTopic, qStudyTopic)
                .join(qStudyTopic.commonCode, qCommonCode)
                .where(
                        qMember.memberIdx.eq(memberIdx),
                        qMemberInterest.memberIsSelected.eq(true)
                )
                .groupBy(qCommonCode.codeIdx)
                .fetch();
    }
}
