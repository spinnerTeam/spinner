package com.spinner.www.member.dto;

import com.spinner.www.member.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberCreateDto {

    private MemberRole memberRole;
    private String email;
    private String password;
    private String nickName;
    private String name;
    private String birth;
}
