package com.spinner.www.member.repository;

import com.spinner.www.member.entity.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepo extends JpaRepository<EmailLog, Long> {
}
