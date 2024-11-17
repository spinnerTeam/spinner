package com.spinner.www.users.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    private String memberEmail;
    @JsonProperty("memberPassword")
    private String memberPassword;
}
