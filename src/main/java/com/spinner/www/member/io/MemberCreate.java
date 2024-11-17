package com.spinner.www.member.io;

import com.spinner.www.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberCreate {

    private MemberRole memberRole;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private LocalDate memberBirth;
}
