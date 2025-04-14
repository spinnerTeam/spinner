package com.spinner.www.member.repository;

import com.spinner.www.member.constants.MemberStatus;
import com.spinner.www.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long> {

    /**
     * user 이메일 조회
     * @param memberEmail String
     * @return boolean
     */
    boolean existsByMemberEmail(String memberEmail);

    /**
     * user 객체 조회
     * @param memberEmail String
     * @return User
     */
    Member findByMemberEmail(String memberEmail);

    /**
     * 멤버 상태 별 조회
     * @param memberStatus MemberStatus
     * @return List<Member>
     */
    List<Member> findByMemberStatus(MemberStatus memberStatus);
}
