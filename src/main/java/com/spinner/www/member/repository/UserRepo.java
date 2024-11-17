package com.spinner.www.member.repository;

import com.spinner.www.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Member, Long> {

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
}
