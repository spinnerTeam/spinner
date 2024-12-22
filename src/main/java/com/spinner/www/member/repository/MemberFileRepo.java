package com.spinner.www.member.repository;

import com.spinner.www.member.entity.MemberFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFileRepo extends JpaRepository<MemberFile, Long> {
}
