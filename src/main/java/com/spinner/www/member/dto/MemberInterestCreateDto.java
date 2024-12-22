package com.spinner.www.member.dto;

import com.spinner.www.common.entity.Menu;
import com.spinner.www.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberInterestCreateDto {

    private Member member;
    private Menu menu;

    public MemberInterestCreateDto(Menu menu, Member member){
        this.menu = menu;
        this.member = member;
    }
}
