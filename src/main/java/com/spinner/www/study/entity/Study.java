package com.spinner.www.study.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.file.entity.Files;
import com.spinner.www.study.dto.StudyCreateDto;
import com.spinner.www.study.dto.StudyUpdateDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "study")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("스터디 관리")
public class Study extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("스터디 PK")
    private Long studyIdx;

    @Comment("스터디명")
    private String studyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fileIdx", nullable = false)
    @Comment("스터디 대표 이미지")
    private Files files;

    @Comment("스터디 소개")
    private String studyInfo;

    @Comment("스터디 삭제 여부")
    private boolean studyIsRemoved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyTopicIdx", nullable = false)
    @Comment("스터디 주제")
    private StudyTopic studyTopic;

    @Comment("스터디 최대 인원")
    private Integer studyMaxPeople;

    /**
     * 스터디 생성
     * @param studyCreateDto StudyCreateDto
     * @return Study
     */
    public static Study insertStudy(StudyCreateDto studyCreateDto){
        return Study.builder()
                .studyName(studyCreateDto.getStudyName())
                .studyInfo(studyCreateDto.getStudyInfo())
                .studyMaxPeople(studyCreateDto.getStudyMaxPeople())
                .studyIsRemoved(false)
                .studyTopic(studyCreateDto.getStudyTopic())
                .files(studyCreateDto.getFiles())
                .build();
    }

    /**
     * 스터디 수정
     * @param studyUpdateDto studyUpdateDto
     */
    public void updateStudy(StudyUpdateDto studyUpdateDto){
        this.studyName = studyUpdateDto.getStudyName();
        this.studyInfo = studyUpdateDto.getStudyInfo();
        this.studyTopic = studyUpdateDto.getStudyTopic();
        this.studyMaxPeople = studyUpdateDto.getStudyMaxPeople();
    }
}
