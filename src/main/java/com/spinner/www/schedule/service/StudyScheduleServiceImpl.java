package com.spinner.www.schedule.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.schedule.dto.StudyScheduleDto;
import com.spinner.www.schedule.repository.StudyScheduleQueryRepo;
import com.spinner.www.schedule.repository.StudyScheduleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyScheduleServiceImpl implements StudyScheduleService{

    private final StudyScheduleRepo studyScheduleRepo;
    private final StudyScheduleQueryRepo studyScheduleQueryRepo;


    @Override
    public List<StudyScheduleDto> findSchedulesByStudyAndMonth(Long studyIdx, int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return studyScheduleQueryRepo.findStudyScheduleByStudyIdxAndMonth(studyIdx, startDate, endDate);
    }
}
