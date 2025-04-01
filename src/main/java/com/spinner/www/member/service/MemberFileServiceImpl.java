package com.spinner.www.member.service;

import com.spinner.www.file.entity.Files;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.MemberFile;
import com.spinner.www.member.repository.MemberFileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberFileServiceImpl implements MemberFileService {

    private final MemberFileRepo memberFileRepo;

    /**
     * MemberFile 객체 생성
     * @param member Member
     * @param files Files
     */
    @Override
    public void create(Member member, Files files){
        MemberFile memberFile = MemberFile.insertMemberFile(member, files);
        memberFileRepo.save(memberFile);
    }

    /**
     * Member 객체를 기준으로 MemberFile 객체 조회
     * @param member Member
     * @return MemberFile
     */
    @Override
    public MemberFile getMemberFile(Member member) {
        return memberFileRepo.findByMember(member);
    }
}
