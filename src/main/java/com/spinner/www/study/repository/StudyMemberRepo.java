package com.spinner.www.study.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.entity.StudyMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepo extends JpaRepository<StudyMember, Long> {

    Optional<StudyMember> findByMember(Member member);
}
