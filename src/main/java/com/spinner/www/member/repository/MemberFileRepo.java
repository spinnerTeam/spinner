package com.spinner.www.member.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.MemberFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFileRepo extends JpaRepository<MemberFile, Long> {

    /**
     * Member 객체를 기준으로 MemberFile 객체 조회
     * @param member Member
     * @return MemberFile
     */
    MemberFile findByMember(Member member);
}
