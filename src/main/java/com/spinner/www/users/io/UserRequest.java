package com.spinner.www.users.io;

import com.spinner.www.users.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserRequest {

    private UserRole userRole;
    private String email;
    private String uPassword;
    private String uName;
    private String uNickname;
    private LocalDate uBirth;
}
