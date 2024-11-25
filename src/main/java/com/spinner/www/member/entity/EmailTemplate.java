package com.spinner.www.member.entity;


import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.constants.EmailTemplateType;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

/**
 * 이메일 템플릿 테이블
 */
@Entity
@Table(name = "emailTemplate")
@Comment("이메일 템플릿 테이블")
@Getter
public class EmailTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("이메일 템플릿 PK")
    private Integer emailTemplateIdx;
    private String emailTemplateName;
    private String emailTemplateTitle;
    private String emailTemplateBody;
    @Enumerated(EnumType.STRING)
    private EmailTemplateType emailTemplateType;

}
