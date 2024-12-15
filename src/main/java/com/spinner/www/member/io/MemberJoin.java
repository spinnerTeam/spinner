package com.spinner.www.member.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberJoin {
    private String password;
    private String passwordConfirm;
}
