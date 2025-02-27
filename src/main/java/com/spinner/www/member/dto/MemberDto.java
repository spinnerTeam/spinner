package com.spinner.www.member.dto;

import com.spinner.www.member.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class MemberDto {

    private Long memberIdx;
    private MemberRole memberRole;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberNickname;
    private LocalDate memberBirth;
}
