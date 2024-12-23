package com.spinner.www.member.dto;

import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.ServiceTerms;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class MarketingCreateDto {

    private Long marketingIdx;
    private ServiceTerms serviceTerms;
    private ZonedDateTime marketingDate;
    private Member member;
    private boolean marketingConsent;

    public MarketingCreateDto (ServiceTerms serviceTerms, boolean marketingConsent, Member member){
        this.serviceTerms = serviceTerms;
        this.marketingConsent = marketingConsent;
        this.member = member;
    }
}
