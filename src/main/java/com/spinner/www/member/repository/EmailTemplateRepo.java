package com.spinner.www.member.repository;

import com.spinner.www.member.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepo extends JpaRepository<EmailTemplate, Integer> {
}
