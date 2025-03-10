package com.spinner.www.study.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMySearchStatusType;
import com.spinner.www.study.constants.StudyStatusType;
import com.spinner.www.study.entity.Study;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyRepo extends JpaRepository<Study, Long> {

}
