package com.spinner.www.report.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.board.entity.Board;
import com.spinner.www.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepo extends JpaRepository<Report, Long> {
    Optional<Report> findByBoardAndMember(Board board, Member member);
}
