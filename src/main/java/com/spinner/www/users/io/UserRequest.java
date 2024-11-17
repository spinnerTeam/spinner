package com.spinner.www.users.io;

import com.spinner.www.users.entity.MemberRole;
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

    private MemberRole memberRole;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private LocalDate memberBirth;
}
