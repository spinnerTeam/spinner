package com.spinner.www.study.repository;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepo extends JpaRepository<Study, Long> {

    /**
     * 스터디명 존재 여부
     * @param studyName String
     * @return boolean
     */
    boolean existsByStudyName(String studyName);

    /**
     * 스터디 idx 존재 여부
     * @param studyIdx Long
     * @return boolean
     */
    boolean existsByStudyIdx(Long studyIdx);
}
