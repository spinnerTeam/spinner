package com.spinner.www.study.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.entity.StudyMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudyMemberRepo extends JpaRepository<StudyMember, Long> {
    Optional<StudyMember> findByMember(Member member);
    Optional<StudyMember> findByStudyAndMember(Study study, Member member);
}
