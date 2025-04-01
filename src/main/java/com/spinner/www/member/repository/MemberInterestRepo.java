package com.spinner.www.member.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberInterestRepo extends JpaRepository<MemberInterest, Long> {
    /**
     * Member 객체를 기준으로 MemberFile 객체 조회
     * @param member Member
     * @return MemberInterest
     */
    List<MemberInterest> findAllByMember(Member member);
}
