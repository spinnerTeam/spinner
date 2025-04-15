package com.spinner.www.common.service;

import com.spinner.www.common.dto.StudyTopicNameListDto;
import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.MyStudyListDto;
import com.spinner.www.study.dto.PendingStudyMemberDto;

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

    /**
     * 스터디 주제 리스트
     * @return List<StudyTopicNameListDto>
     */
    List<StudyTopicNameListDto> getStudyTopicName();

    /**
     * 멤버 1명의 관심 분야
     * @param memberIdx
     * @return List<Long> Long
     */

    public List<Long> findInterestCodeIdxByMember(Long memberIdx);
}
