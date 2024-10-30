package com.spinner.www.users.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserLoginDto {

    private String email;
    @JsonProperty("upassword")
    private String uPassword;
}
