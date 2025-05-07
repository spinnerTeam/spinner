package com.spinner.www.schedule.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class StudyScheduleDto {

    private Long idx;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;


    @QueryProjection
    public StudyScheduleDto(Long idx, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.idx = idx;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
