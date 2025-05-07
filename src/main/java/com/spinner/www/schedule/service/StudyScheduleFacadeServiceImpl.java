package com.spinner.www.schedule.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.schedule.dto.StudyScheduleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyScheduleFacadeServiceImpl implements StudyScheduleFacadeService{

    private final StudyScheduleService studyScheduleService;

    @Override
    public ResponseEntity<CommonResponse> findSchedulesByStudyAndMonth(Long studyIdx, int year, int month) {

        List<StudyScheduleDto> studyScheduleDtos = studyScheduleService.findSchedulesByStudyAndMonth(studyIdx, year, month);

        return null;
    }
}
