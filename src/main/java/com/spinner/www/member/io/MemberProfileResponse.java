package com.spinner.www.member.io;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class MemberProfileResponse {
    private String nickName;
    private String birth;
    private String profileImageUrl;
    private List<MemberInterestResponse> interests;
}
