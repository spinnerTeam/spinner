package com.spinner.www.study.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.file.entity.Files;
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

    @Comment("스터디 삭제 여부")
    private boolean studyIsRemoved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyTopicIdx", nullable = false)
    @Comment("스터디 주제")
    private StudyTopic studyTopic;

    @Comment("스터디 최대 인원")
    private Integer studyMaxPeople;

}
