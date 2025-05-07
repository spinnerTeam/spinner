package com.spinner.www.schedule.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.schedule.dto.StudyScheduleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudyScheduleService {


    List<StudyScheduleDto> findSchedulesByStudyAndMonth(Long studyIdx, int year, int month);
}
