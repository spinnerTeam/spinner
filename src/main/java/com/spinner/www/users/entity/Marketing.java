package com.spinner.www.users.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.time.ZonedDateTime;

/**
 * 마케팅 수신 동의
 */
@Entity
@Table(name = "marketing")
@Comment("마케팅 수신 동의 테이블")
public class Marketing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("마케팅 PK")
    private Long mIdx;

    @ManyToOne
    @JoinColumn(name = "stIdx")
    @Comment("약관 테이블")
    private ServiceTerms serviceTerms;

    @Comment("동의 or 거절 날짜")
    private ZonedDateTime mDate;

    @ManyToOne
    @JoinColumn(name = "uIdx")
    @Comment("유저 테이블")
    private Users users;

    @Comment("동의 여부")
    private boolean mConsent;
}
