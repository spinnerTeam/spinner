package com.spinner.www.study.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.common.entity.QCommonCode;
import com.spinner.www.common.entity.QStudyTopic;
import com.spinner.www.file.entity.QFiles;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.QMember;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.*;
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

    /**
     * 관심분야 별 가입가능 스터디 조회 (랜덤 노출)
     * @param codeList List<Long>
     * @return List<StudyListDto>
     */
    public List<StudyListDto> findInterestCodeByStudy(List<Long> codeList){
        QStudy qStudy = QStudy.study;
        QStudyTopic qStudyTopic = QStudyTopic.studyTopic;
        QCommonCode qCommonCode = QCommonCode.commonCode;
        QStudyMember qStudyMember = QStudyMember.studyMember;
        QFiles qFiles = QFiles.files;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 공통조건
        booleanBuilder.and(qStudyMember.isStudyMemberRemoved.eq(false));
        booleanBuilder.and(qStudyMember.studyMemberStatus.eq(StudyMemberStatus.APPROVED));
        booleanBuilder.and(qStudyMember.isStudyMemberRemoved.eq(false));

        // 정렬 조건
        OrderSpecifier<?> orderSpecifier = qStudy.createdAt.desc();

        // 조건 분기
        if(codeList != null && !codeList.isEmpty()){
            booleanBuilder.and(qStudy.studyTopic.studyTopicIdx.in(codeList));
            orderSpecifier = Expressions.numberTemplate(Double.class, "rand()").asc();
        }
        return jpaQueryFactory
                .select(new QStudyListDto(
                        qStudy.studyIdx,
                        qFiles.filePath,
                        qStudy.studyName,
                        qStudy.studyInfo,
                        qCommonCode.codeName
                ))
                .from(qStudy)
                .join(qStudy.studyTopic, qStudyTopic)
                .join(qStudyTopic.commonCode, qCommonCode)
                .join(qStudy.studyMembers , qStudyMember)
                .join(qStudy.files, qFiles)
                .where(booleanBuilder)
                .groupBy(qStudy.studyIdx)
                .orderBy(orderSpecifier)
                .fetch();
    }

    /**
     * 스터디 상세조회
     * @param studyIdx Long
     * @return StudyListDetailDto
     */
//    public StudyListDetailDto viewStudyList(Long studyIdx){
//        QStudy qStudy = QStudy.study;
//        QStudyTopic qStudyTopic = QStudyTopic.studyTopic;
//        QCommonCode qCommonCode = QCommonCode.commonCode;
//        QFiles qFiles = QFiles.files;
//        QStudyMember qStudyMember = QStudyMember.studyMember;
//
//        return jpaQueryFactory.select(new QStudyListDetailDto(
//
//
//        ))
//                .from(qStudy)
//                .join(qStudy.studyTopic, qStudyTopic)
//                .join(qStudyTopic.commonCode, qCommonCode)
//                .join(qStudy.files, qFiles)
//                .join(qStudy.studyMembers , qStudyMember)
//                .where(
//                        qStudy.studyIdx.eq(studyIdx)
//                )
//                .groupBy(qStudy.studyIdx)
//                .fetchOne();
//    }

    /**
     * 스터디별 멤버 조회
     * @param studyIdx Long
     * @return StudyMember
     */
    public StudyMember getStudyByStudyMember(Long studyIdx){

        QStudyMember qStudyMember = QStudyMember.studyMember;
        QMember qMember = QMember.member;

        return null;
    }
}
