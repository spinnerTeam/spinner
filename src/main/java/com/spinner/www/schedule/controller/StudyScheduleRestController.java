package com.spinner.www.schedule.controller;

import com.spinner.www.common.io.CommonResponse;

import com.spinner.www.schedule.service.StudyScheduleFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "studySchedule", description = "일정 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/schedule")
public class StudyScheduleRestController {

    private final StudyScheduleFacadeService studyScheduleFacadeService;

    /**
     * 스터디 월별 일정 조회
     *
     * <p>특정 스터디에 등록된 일정 중, 요청한 연/월에 해당하는 일정 목록을 조회합니다.</p>
     *
     * @param studyIdx 스터디 식별자 (PK)
     * @param year     조회할 연도 (예: 2025)
     * @param month    조회할 월 (1~12)
     * @return CommonResponse 형태로 일정 리스트 반환
     */
    @Operation(
            summary = "스터디 월별 일정 조회 API",
            description = "스터디 ID와 연/월을 기준으로 해당 월의 일정을 조회합니다."
    )
    @Parameters({
            @Parameter(name = "studyIdx", description = "스터디 PK"),
            @Parameter(name = "year", description = "조회할 연도 (예: 2025)"),
            @Parameter(name = "month", description = "조회할 월 (1~12)")
    })
    @GetMapping("/view/{studyIdx}/{year}/{month}")
    public ResponseEntity<CommonResponse> getStudySchedule(@PathVariable Long studyIdx, @PathVariable int year, @PathVariable int month){
        return studyScheduleFacadeService.findSchedulesByStudyAndMonth(studyIdx , year, month);
    }
}
