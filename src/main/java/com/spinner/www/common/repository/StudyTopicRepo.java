package com.spinner.www.common.repository;

import com.spinner.www.common.entity.StudyTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudyTopicRepo extends JpaRepository<StudyTopic, Long> {

    /**
     * 스터디 주제 조회
     * @param menuIdx Long
     * @return Optional<StudyTopic>
     */
    Optional<StudyTopic> findByStudyTopicIdx(Long menuIdx);

    /**
     * 부모 idx 가 있는 스터디 조회
     * @return List<StudyTopic>
     */
    List<StudyTopic> findAllByStudyTopicParentIdxIsNotNull();

    List<StudyTopic> findByStudyTopicDepthAndStudyTopicParentIdx(Integer depth, Long parentIdx);
}
