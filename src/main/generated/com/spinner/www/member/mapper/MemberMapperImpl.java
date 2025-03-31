package com.spinner.www.member.mapper;

import com.spinner.www.member.dto.MemberCreateDto;
import com.spinner.www.member.dto.MemberDto;
import com.spinner.www.member.dto.MemberSessionDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.io.MemberJoin;
import com.spinner.www.member.io.MemberLogin;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:02+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberSessionDto memberToMemberSessionDTO(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberSessionDto memberSessionDto = new MemberSessionDto();

        memberSessionDto.setMemberIdx( member.getMemberIdx() );
        memberSessionDto.setMemberRole( member.getMemberRole() );
        memberSessionDto.setMemberEmail( member.getMemberEmail() );
        memberSessionDto.setMemberPassword( member.getMemberPassword() );
        memberSessionDto.setMemberNickname( member.getMemberNickname() );

        return memberSessionDto;
    }

    @Override
    public MemberSessionDto memberLoginToMemberSessionDto(MemberLogin memberLogin) {
        if ( memberLogin == null ) {
            return null;
        }

        MemberSessionDto memberSessionDto = new MemberSessionDto();

        memberSessionDto.setMemberEmail( memberLogin.getEmail() );
        memberSessionDto.setMemberPassword( memberLogin.getPassword() );

        return memberSessionDto;
    }

    @Override
    public MemberCreateDto memberJoinToMemberCreate(MemberJoin memberJoin) {
        if ( memberJoin == null ) {
            return null;
        }

        MemberCreateDto memberCreateDto = new MemberCreateDto();

        memberCreateDto.setPassword( memberJoin.getPassword() );
        memberCreateDto.setNickName( memberJoin.getNickName() );
        memberCreateDto.setName( memberJoin.getName() );
        memberCreateDto.setBirth( memberJoin.getBirth() );

        return memberCreateDto;
    }

    @Override
    public MemberDto memberToMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto memberDto = new MemberDto();

        memberDto.setMemberIdx( member.getMemberIdx() );
        memberDto.setMemberRole( member.getMemberRole() );
        memberDto.setMemberEmail( member.getMemberEmail() );
        memberDto.setMemberPassword( member.getMemberPassword() );
        memberDto.setMemberName( member.getMemberName() );
        memberDto.setMemberNickname( member.getMemberNickname() );
        memberDto.setMemberBirth( member.getMemberBirth() );

        return memberDto;
    }
}
