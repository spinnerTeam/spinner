package com.spinner.www.batch.service;

import com.spinner.www.member.constants.MemberStatus;
import com.spinner.www.member.dto.MemberDto;
import com.spinner.www.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 스케쥴러 실제 로직
 */
@Service
@RequiredArgsConstructor
public class MemberScheduleService {

    private final MemberService memberService;

    public void deleteWithdrawMember() {
        List<MemberDto> memberDtos = memberService.findMembersByStatus(MemberStatus.WITHDRAWN);

    }
}
