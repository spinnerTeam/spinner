package com.spinner.www.member.mapper;

import com.spinner.www.member.dto.MemberCreateDto;
import com.spinner.www.member.dto.MemberDto;
import com.spinner.www.member.dto.MemberSessionDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.io.MemberJoin;
import com.spinner.www.member.io.MemberLogin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberSessionDto memberToMemberSessionDTO(Member member);

    @Mappings({
            @Mapping(source = "email", target = "memberEmail"),
            @Mapping(source = "password", target = "memberPassword")
    })
    MemberSessionDto memberLoginToMemberSessionDto(MemberLogin memberLogin);
    MemberCreateDto memberJoinToMemberCreate(MemberJoin memberJoin);
    MemberDto memberToMemberDto(Member member);
}
