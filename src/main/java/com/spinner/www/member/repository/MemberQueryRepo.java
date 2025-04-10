package com.spinner.www.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.entity.QStudy;
import com.spinner.www.study.entity.QStudyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepo {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 스터디별 스터디원 수 조회
     * @param studyIdx Long
     * @return Integer
     */
    public Integer getStudyMemberCountByStudyIdx(Long studyIdx){

        QStudy qStudy = QStudy.study;
        QStudyMember qStudyMember = QStudyMember.studyMember;

        Long count = jpaQueryFactory.select(
                qStudyMember.studyMemberIdx.count()
        )
                .from(qStudy)
                .join(qStudy.studyMembers, qStudyMember)
                .where(
                    qStudy.studyIsRemoved.eq(false),
                    qStudyMember.isStudyMemberRemoved.eq(false),
                    qStudyMember.studyMemberStatus.eq(StudyMemberStatus.APPROVED),
                    qStudy.studyIdx.eq(studyIdx)
                ).fetchOne();

        return count != null ? count.intValue() : 0;
    }
}
