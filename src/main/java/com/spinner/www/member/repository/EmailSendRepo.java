package com.spinner.www.member.repository;

import com.spinner.www.member.entity.EmailSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailSendRepo extends JpaRepository<EmailSend, Long> {
}
