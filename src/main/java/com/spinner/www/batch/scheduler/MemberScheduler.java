package com.spinner.www.batch.scheduler;

import com.spinner.www.batch.service.MemberScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 스케쥴러 트리거
 */
@Component
@RequiredArgsConstructor
public class MemberScheduler {

    private final MemberScheduleService memberScheduleService;

    public void deleteWithdrawMember(){
        memberScheduleService.deleteWithdrawMember();
    }
}
