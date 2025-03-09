package com.spinner.www.study.repository;

import static com.spinner.www.member.entity.QMember.member;
import static com.spinner.www.member.entity.QMemberFile.memberFile;
import static com.spinner.www.study.entity.QStudy.study;
import static com.spinner.www.study.entity.QStudyMember.studyMember;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.study.constants.StudyMemberStatusType;
import com.spinner.www.study.dto.QStudyMemberSelectDto;
import com.spinner.www.study.dto.StudyMemberSelectDto;
import com.spinner.www.study.entity.QStudyMember;
import com.spinner.www.study.entity.Study;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyMemberQueryRepo {

    private final JPAQueryFactory queryFactory;


    public List<StudyMemberSelectDto> findStudyMember(Study joinStudy) {

        return queryFactory
            .select(new QStudyMemberSelectDto(
                member.memberIdx,
                memberFile.memberFileIdx,
                member.memberName))
            .from(studyMember)
            .join(studyMember.member, member)
            .leftJoin(memberFile)
            .on(member.eq(memberFile.member)) // 필요하면 leftJoin
            .where(studyMember.study.eq(joinStudy),
                studyMember.studyMemberStatus.eq(StudyMemberStatusType.JOIN),
                studyMember.studyMemberRemoved.eq("N"))
            .orderBy(studyMember.createdDate.asc())
            .fetch();
    }

}
