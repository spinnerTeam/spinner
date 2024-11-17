package com.spinner.www.users.entity;


import org.hibernate.annotations.Comment;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
    private Long marketingIdx;

    @ManyToOne
    @JoinColumn(name = "serviceTermsIdx")
    @Comment("약관 테이블")
    private ServiceTerms serviceTerms;

    @Comment("동의 or 거절 날짜")
    private ZonedDateTime marketingDate;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    @Comment("유저 테이블")
    private Member member;

    @Comment("동의 여부")
    private boolean marketingConsent;
}
