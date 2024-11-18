package com.spinner.www.member.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

/**
 * 이메일 발송 로그
 */
@Entity
@Table(name = "emailLog")
@Comment("이메일 로그 테이블")
public class EmailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("이메일 발송 로그 PK")
    private Long emailLogIdx;
    @ManyToOne
    @JoinColumn(name = "emailSendIdx")
    private EmailSend emailSend;
    @ManyToOne
    @JoinColumn(name = "emailTemplateIdx")
    private EmailTemplate emailTemplate;
    private boolean emailLogStatus;
}
