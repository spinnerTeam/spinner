package com.spinner.www.member.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.constants.ServiceTermsType;
import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

@Entity
@Table(name = "serviceTerms")
@Comment("약관 테이블")
public class ServiceTerms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("약관PK")
    private Long serviceTermsIdx;

    @Comment("필수 여부")
    @Enumerated(EnumType.STRING)
    private ServiceTermsType serviceTermsType;
    @Comment("약관내용")
    private String serviceTermsContent;
    @Comment("약관제목")
    private String serviceTermsTitle;
    @Comment("사용여부")
    private boolean serviceTermsIsUse;
}
