package com.spinner.www.member.dto;

import com.spinner.www.member.constants.MemberStatus;
import com.spinner.www.member.entity.MemberRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

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
    private MemberStatus memberStatus;
    private LocalDate withdrawalDate;

}
