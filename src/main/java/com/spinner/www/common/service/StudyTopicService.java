package com.spinner.www.common.service;

import com.spinner.www.common.entity.StudyTopic;

import java.util.List;
import java.util.Optional;

public interface StudyTopicService {

    /**
     * studyTopicIdx 로 studyTopic 조회
     * @param menuIdx Long
     * @return Menu
     */
    Optional<StudyTopic> getStudyTopicByStudyTopicIdx(Long menuIdx);

    /**
     * 전체 studyTopic 조회
     * @return List<Menu>
     */
    List<StudyTopic> getAll();

    /**
     * 전체 studyTopic 조회
     * @return List<Menu>
     */
    List<StudyTopic> getAllInterest();
}
