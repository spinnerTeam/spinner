package com.spinner.www.schedule.service;

import com.spinner.www.common.io.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface StudyScheduleFacadeService {

    ResponseEntity<CommonResponse> findSchedulesByStudyAndMonth(Long studyIdx, int year, int month);
}
