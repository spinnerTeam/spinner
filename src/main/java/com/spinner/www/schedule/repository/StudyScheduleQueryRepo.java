package com.spinner.www.schedule.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.schedule.dto.QStudyScheduleDto;
import com.spinner.www.schedule.dto.StudyScheduleDto;
import com.spinner.www.schedule.entity.QStudySchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudyScheduleQueryRepo {

    private final JPAQueryFactory jpaQueryFactory;

    public List<StudyScheduleDto> findStudyScheduleByStudyIdxAndMonth(Long studyIdx, LocalDate startDate, LocalDate endDate){

        QStudySchedule QSchedule = QStudySchedule.studySchedule;

        return jpaQueryFactory
                .select(new QStudyScheduleDto(
                        QSchedule.studyScheduleIdx,
                        QSchedule.studyScheduleTitle,
                        QSchedule.studyScheduleDescription,
                        QSchedule.studyScheduleStartDate,
                        QSchedule.studyScheduleEndDate
                ))
                .from(QSchedule)
                .where(
                        QSchedule.study.studyIdx.eq(studyIdx),
                        QSchedule.studyScheduleStartDate.between(startDate, endDate)
                )
                .fetch();
    }
}
