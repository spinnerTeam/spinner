package com.spinner.www.member.repository;

import com.spinner.www.member.entity.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInterestRepo extends JpaRepository<MemberInterest, Long> {
}
