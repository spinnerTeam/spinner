package com.spinner.www.study.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.common.entity.CommonCode;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatusType;
import com.spinner.www.study.constants.StudyMySearchStatusType;
import com.spinner.www.study.constants.StudySortType;
import com.spinner.www.study.constants.StudyStatusType;
import com.spinner.www.study.dto.QStudySearchDto;
import com.spinner.www.study.dto.StudySearchDto;
import com.spinner.www.study.dto.StudySearchParamDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import static com.spinner.www.common.entity.QCommonCode.*;
import static com.spinner.www.common.entity.QMenu.*;
import static com.spinner.www.member.entity.QMember.*;
import static com.spinner.www.member.entity.QMemberInterest.*;
import static com.spinner.www.study.entity.QStudy.*;
import static com.spinner.www.study.entity.QStudyMember.*;


@Repository
@RequiredArgsConstructor
public class StudyQueryRepo {

    private final JPAQueryFactory queryFactory;

    public List<StudySearchDto> studySearch(Pageable pageable, Member loginMember,
        StudySearchParamDto studySearchParamDto) {

        // 로그인 유저의 관심분야 가져오는 로직
        CommonCode loginUserInterest = queryFactory
            .select(menu.commonCode)
            .from(menu)
            .join(memberInterest).fetchJoin()
            .on(member.memberIdx.eq(loginMember.getMemberIdx()))
            .join(commonCode).fetchJoin()
            .on(commonCode.eq(menu.commonCode))
            .where(menu.menuIdx.eq(memberInterest.menu.menuIdx))
            .fetchFirst();

        // 최신순
        if (studySearchParamDto.getSortType().equals(StudySortType.desc)) {
            return queryFactory
                .select(new QStudySearchDto(
                    study.id,
                    study.studyName,
                    study.studyIntro,
                    study.files))
                .from(study)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(study.createdDate.desc())
                .where(studyNameLike(studySearchParamDto))
                .fetch();

        // 인기 많은 순
        } else if (studySearchParamDto.getSortType().equals(StudySortType.like)) {
            return queryFactory
                .select(new QStudySearchDto(
                    study.id,
                    study.studyName,
                    study.studyIntro,
                    study.files))
                .from(study)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(study.studyViews.asc())
                .where(studyNameLike(studySearchParamDto))
                .fetch();
        }

        // 기본 관심분야 리스트 반환
        return queryFactory
            .select(new QStudySearchDto(
                study.id,
                study.studyName,
                study.studyIntro,
                study.files))
            .from(study)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(study.common.eq(loginUserInterest).desc(),
                study.createdDate.desc())
            .where(studyNameLike(studySearchParamDto))
            .fetch();
    }

    private BooleanExpression studyNameLike(StudySearchParamDto studySearchParamDto) {
        return StringUtils.hasText(studySearchParamDto.getKeyword()) ? study.studyName.contains(
            studySearchParamDto.getKeyword()) : null;
    }

    public List<StudySearchDto> myStudySearch(Pageable pageable, Member member,
        StudyMySearchStatusType studyMySearchStatusType) {

        StudyStatusType studyStatusType = studyMySearchStatusType.getStudyStatusType();
        StudyMemberStatusType studyMemberStatusType = studyMySearchStatusType.getStudyMemberStatusType();

        return queryFactory
            .select(new QStudySearchDto(
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
