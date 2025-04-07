package com.spinner.www.common.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.common.entity.QCommonCode;
import com.spinner.www.common.entity.QStudyTopic;
import com.spinner.www.common.entity.StudyTopic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudyTopicQueryRepo {

    private final JPAQueryFactory jpaQueryFactory;

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
}
