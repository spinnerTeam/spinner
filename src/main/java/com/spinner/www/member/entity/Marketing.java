package com.spinner.www.member.entity;


import com.spinner.www.member.dto.MarketingCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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

    public static Marketing insertMarketing(MarketingCreateDto marketing){
        return Marketing.builder()
                .serviceTerms(marketing.getServiceTerms())
                .marketingDate(ZonedDateTime.now())
                .member(marketing.getMember())
                .marketingConsent(marketing.isMarketingConsent())
                .build();
    }
}
