package com.spinner.www.member.repository;

import com.spinner.www.member.entity.Marketing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketingRepo extends JpaRepository<Marketing, Long> {
}
