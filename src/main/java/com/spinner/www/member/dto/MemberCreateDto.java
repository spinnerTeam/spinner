package com.spinner.www.member.dto;

import com.spinner.www.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDto {

    private MemberRole memberRole;
    private String email;
    private String password;
    private String nickName;
    private String name;
    private String birth;

    public MemberCreateDto (MemberCreateDto memberCreateDto, String email, MemberRole memberRole){
        this.memberRole = memberRole;
        this.email = email;
        this.password = memberCreateDto.getPassword();
        this.nickName = memberCreateDto.getNickName();
        this.name = memberCreateDto.getName();
        this.birth = memberCreateDto.getBirth();
    }
}
