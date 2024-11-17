package com.spinner.www.member.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemberLogin {

    private String memberEmail;
    @JsonProperty("memberPassword")
    private String memberPassword;
}
