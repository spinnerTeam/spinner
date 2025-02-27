package com.spinner.www.study.config;

import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.config.annotation.StudyLeaderOnly;
import com.spinner.www.study.constants.StudyMemberRoleType;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.repository.StudyMemberRepository;
import com.spinner.www.study.repository.StudyRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class StudyLeaderOnlyAspect {

    private final StudyRepository studyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final SessionInfo sessionInfo;

    @Around("@annotation(com.spinner.www.study.config.annotation.StudyLeaderOnly)")
    public Object checkStudyLeader(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs(); // 메서드 인자 가져오기

        Long id = Arrays.stream(args)
            .filter(Long.class::isInstance) // Long 타입만 필터링
            .map(Long.class::cast) // Long 변환
            .findFirst() // 첫 번째 Long 타입 값을 찾음
            .orElseThrow(() -> new IllegalArgumentException("스터디 ID가 필요합니다.")); // 없으면 예외 던지기

        // 현재 로그인된 사용자 가져오기
        Long memberIdx = sessionInfo.getMemberIdx();

        if (memberIdx == null) {
            throw new IllegalArgumentException("현재 로그인된 사용자가 아닙니다.");
        }

        // 스터디 정보를 조회하여 스터디장인지 확인
        studyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 스터디입니다."));

        Member member = Member.builder()
            .memberIdx(memberIdx)
            .build();

        StudyMember studyMember = studyMemberRepository.findByMember(member)
            .orElseThrow(() -> new IllegalArgumentException("스터디에 가입되어 있지 않은 이용자입니다."));

        if (!studyMember.getStudyMemberRole().equals(StudyMemberRoleType.leader)) {
            throw new IllegalArgumentException("스터디장만 수정할 수 있습니다.") {
            };
        }

        return joinPoint.proceed(); // 메서드 실행
    }
}
