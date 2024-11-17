package com.spinner.www.member.mapper;

import com.spinner.www.member.dto.MemberLoginDto;
import com.spinner.www.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberLoginDto memberToMemberLoginDTO(Member member);


}
