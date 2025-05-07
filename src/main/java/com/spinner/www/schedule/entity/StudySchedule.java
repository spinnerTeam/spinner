package com.spinner.www.schedule.entity;

import com.spinner.www.study.entity.Study;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Table(name = "study_schedule")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Comment("일정 테이블")
public class StudySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_schedule_idx")
    @Comment("스터디 일정 식별자")
    private Long studyScheduleIdx;

    @Column(name = "study_schedule_title")
    @Comment("일정명")
    private String studyScheduleTitle;

    @Column(name = "study_schedule_description")
    @Comment("일정내용")
    private String studyScheduleDescription;

    @Column(name = "study_schedule_start_date")
    @Comment("일정시작일")
    private LocalDate studyScheduleStartDate;

    @Column(name = "study_schedule_end_date")
    @Comment("일정종료일")
    private LocalDate studyScheduleEndDate;

    @Column(name = "study_schedule_time")
    @Comment("일정시간")
    private String studyScheduleTime;

    @Column(name = "study_schedule_place")
    @Comment("일정장소")
    private String studySchedulePlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_schedule_study_idx")
    @Comment("스터디 식별자 (FK)")
    private Study study;
}
