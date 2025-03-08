package com.spinner.www.study.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatusType;
import com.spinner.www.study.constants.StudyMySearchStatusType;
import com.spinner.www.study.constants.StudyStatusType;
import com.spinner.www.study.dto.QStudyMySearchDto;
import com.spinner.www.study.dto.StudyMySearchDto;
import com.spinner.www.study.entity.Study;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import static com.spinner.www.study.entity.QStudy.*;
import static com.spinner.www.study.entity.QStudyMember.*;


@Repository
@RequiredArgsConstructor
public class StudyQueryRepo {

    private final JPAQueryFactory queryFactory;

    public List<StudyMySearchDto> myStudySearch(Pageable pageable, Member member,
        StudyMySearchStatusType studyMySearchStatusType) {

        StudyStatusType studyStatusType = studyMySearchStatusType.getStudyStatusType();
        StudyMemberStatusType studyMemberStatusType = studyMySearchStatusType.getStudyMemberStatusType();

        return queryFactory
            .select(new QStudyMySearchDto(
                study.id,
                study.studyName,
                study.studyIntro,
                study.files))
            .from(study)
            .join(studyMember).fetchJoin()
            .on(studyMember.member.eq(member))
            .on(studyMember.studyMemberStatus.eq(studyMemberStatusType))
            .where(study.studyStatusType.eq(studyStatusType))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}
