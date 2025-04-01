package com.spinner.www.member.service;

import com.spinner.www.file.entity.Files;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.MemberFile;

public interface MemberFileService {

    /**
     * MemberFile 객체 생성
     * @param member Member
     * @param files Files
     */
    void create(Member member, Files files);

    /**
     * Member 객체를 기준으로 MemberFile 객체 조회
     * @param member Member
     * @return MemberFile
     */
    MemberFile getMemberFile(Member member);


}
