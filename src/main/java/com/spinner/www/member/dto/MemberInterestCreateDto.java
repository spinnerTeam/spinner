package com.spinner.www.member.dto;

import com.spinner.www.common.entity.Menu;
import com.spinner.www.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MemberInterestCreateDto {

    private Menu menu;
    private Member member;

    public MemberInterestCreateDto(Menu menu, Member member){
        this.menu = menu;
        this.member = member;
    }
}
