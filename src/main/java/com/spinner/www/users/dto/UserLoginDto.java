package com.spinner.www.users.dto;

import com.spinner.www.users.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {

    private Long uIdx;
    private UserRole userRole;
    private String email;
    private String uPassword;
    private String uNickname;
    private String acessToken;

}
