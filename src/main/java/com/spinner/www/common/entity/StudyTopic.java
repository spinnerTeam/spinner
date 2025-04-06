package com.spinner.www.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "study_topic")
@Comment("메뉴")
@Getter
public class StudyTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("스터디 주제 PK")
    private Long studyTopicIdx;

    @Comment("스터디 뎁스")
    private Integer studyTopicDepth;

    @Comment("부모 스터디 주제 IDX")
    private Long studyTopicParentIdx;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codeIdx")
    @Comment("공통코드 IDX")
    private CommonCode commonCode;
}
