package com.spinner.www.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.ZonedDateTime;

/**
 * 이메일 발송 로그
 */
@Entity
@Table(name = "emailLog")
@Comment("이메일 로그 테이블")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("이메일 발송 로그 PK")
    private Long emailLogIdx;

    private String toEmail;
    @ManyToOne
    @JoinColumn(name = "emailTemplateIdx")
    private EmailTemplate emailTemplate;
    private boolean emailLogStatus;
    private ZonedDateTime emailSendDate;

    public static EmailLog insertEmailLog(String toEmail, EmailTemplate emailTemplate){
        return EmailLog.builder()
                .toEmail(toEmail)
                .emailLogStatus(true)
                .emailTemplate(emailTemplate)
                .emailSendDate(ZonedDateTime.now())
                .build();
    }
}
