package com.spinner.www.users.dto;

import com.spinner.www.users.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    private Long memberIdx;
    private MemberRole memberRole;
    private String memberEmail;
    private String memberPassword;
    private String memberNickname;
    private String acessToken;

}
