package com.spinner.www.member.dto;

import com.spinner.www.member.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSessionDto {

    private Long memberIdx;
    private MemberRole memberRole;
    private String memberEmail;
    private String memberPassword;
    private String memberNickname;
    private String acessToken;

}
