package com.spinner.www.users.dto;

import com.spinner.www.users.entity.UserRole;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserRequestDto {

    private UserRole userRole;
    private String uEmail;
    private String uPw;
    private String uName;
    private String uNickname;
    private LocalDate uBirth;
}
