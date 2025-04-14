package com.spinner.www.member.repository;

import com.spinner.www.member.entity.MemberWithdrawalLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberWithDrawalLogRepo extends JpaRepository<MemberWithdrawalLog,Long> {
}
