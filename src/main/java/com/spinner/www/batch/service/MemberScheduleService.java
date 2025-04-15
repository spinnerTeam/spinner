package com.spinner.www.batch.service;

import com.spinner.www.member.constants.MemberStatus;
import com.spinner.www.member.dto.MemberDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.study.entity.StudyMember;
import com.spinner.www.study.service.StudyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * 스케쥴러 실제 로직
 */
@Service
@RequiredArgsConstructor
public class MemberScheduleService {

    private final MemberService memberService;
    private final StudyMemberService studyMemberService;

    /**
     * 탈퇴 신청일 14일 이후 삭제
     */
    @Transactional
    public void deleteWithdrawMember() {
        List<Member> memberDtos = memberService.findWithdrawnMembersBefore(MemberStatus.WITHDRAWN, LocalDate.now().minusDays(14));
        for(Member member : memberDtos){
            member.softDeleteWithdrawalMember();
        }
        memberService.saveAll(memberDtos);
    }

    /**
     * 스터디 탈퇴 14일 이후 삭제
     */
    @Transactional
    public void deleteWithdrawStudyMember(){
        studyMemberService.deleteWithdrawnStudyMembersBefore(LocalDate.now().minusDays(14));
    }
}
