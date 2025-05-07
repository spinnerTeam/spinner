package com.spinner.www.schedule.repository;

import com.spinner.www.schedule.entity.StudySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyScheduleRepo extends JpaRepository<StudySchedule, Long> {
}
