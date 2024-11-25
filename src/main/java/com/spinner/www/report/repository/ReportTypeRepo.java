package com.spinner.www.report.repository;

import com.spinner.www.report.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTypeRepo extends JpaRepository<ReportType, Long> {
}
