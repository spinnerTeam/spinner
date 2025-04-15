package com.spinner.www.batch.scheduler;

import com.spinner.www.batch.service.MemberScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 스케쥴러 트리거
 */
@Component
@RequiredArgsConstructor
public class MemberScheduler {

    private final MemberScheduleService memberScheduleService;

    /**
     * 매일 밤 12시 실행
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteWithdrawMember(){
        memberScheduleService.deleteWithdrawMember();
    }
}
