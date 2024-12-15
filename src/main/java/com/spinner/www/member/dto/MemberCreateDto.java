package com.spinner.www.member.dto;

import com.spinner.www.member.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateDto {

    private MemberRole memberRole;
    private String memberEmail;
    private String memberPassword;
}
