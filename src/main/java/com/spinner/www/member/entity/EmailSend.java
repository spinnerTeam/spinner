package com.spinner.www.member.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.ZonedDateTime;

/**
 * 이메일
 */
@Entity
@Table(name = "emailSend")
@Comment("이메일 발송 테이블")
public class EmailSend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("이메일 PK")
    private Long emailSendIdx;
    private String toEmail; // 이메이 받는 사람
    private ZonedDateTime emailSendDate;
}
