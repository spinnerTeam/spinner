package com.spinner.www.member.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemberLogin {

    private String email;
    @JsonProperty("password")
    private String password;
}
