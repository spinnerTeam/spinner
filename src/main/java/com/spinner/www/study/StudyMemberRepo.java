package com.spinner.www.study;

import com.spinner.www.study.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMemberRepo extends JpaRepository<StudyMember, Long> {
}
