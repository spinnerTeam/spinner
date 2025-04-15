package com.spinner.www.common.service;

import com.spinner.www.common.dto.StudyTopicNameListDto;
import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.common.repository.StudyTopicQueryRepo;
import com.spinner.www.common.repository.StudyTopicRepo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.dto.MyStudyListDto;
import com.spinner.www.study.dto.PendingStudyMemberDto;
import com.spinner.www.study.repository.StudyQueryRepo;
import com.spinner.www.util.StudyTopicCacheStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudyTopicServiceImpl implements StudyTopicService {

    private final StudyTopicRepo studyTopicRepo;
    private final StudyTopicCacheStorage studyTopicCacheStorage;
    private final StudyTopicQueryRepo studyTopicQueryRepo;

    /**
     * studyTopicIdx 로 StudyTopic 조회
     * @param studyTopicIdx Long
     * @return Optional<StudyTopic>
     */
    @Override
    public Optional<StudyTopic> getStudyTopicByStudyTopicIdx(Long studyTopicIdx) {
        return studyTopicRepo.findByStudyTopicIdx(studyTopicIdx);
    }

    /**
     * 전체 메뉴 조회
     * @return List<Menu>
     */
    @Override
    public List<StudyTopic> getAll() {
        return studyTopicRepo.findAll();
    }

    /**
     * 전체 관심분야 조회(부모 메뉴가 있는 경우)
     * @return List<Menu>
     */
    @Override
    public List<StudyTopic> getAllInterest() {
        return studyTopicRepo.findAllByStudyTopicParentIdxIsNotNull();
    }

    /**
     * 스터디 주제 조회
     * @return  List<StudyTopicNameListDto>
     */
    @Override
    public List<StudyTopicNameListDto> getStudyTopicName() {
        List<StudyTopicNameListDto> studyTopicNameListDto = new ArrayList<>();
        List<StudyTopic> studyTopics = studyTopicCacheStorage.getStudyTopics();
        for(StudyTopic studyTopic : studyTopics){

            StudyTopicNameListDto dto = StudyTopicNameListDto.builder()
                    .studyTopicIdx(studyTopic.getStudyTopicIdx())
                    .studyName(studyTopic.getCommonCode().getCodeName())
                    .build();
            studyTopicNameListDto.add(dto);
        }
        return studyTopicNameListDto;
    }

    /**
     * 멤버 1명의 관심 분야
     * @param memberIdx
     * @return List<Long> Long
     */
    @Override
    public List<Long> findInterestCodeIdxByMember(Long memberIdx) {
        return studyTopicQueryRepo.findInterestCodeIdxByMember(memberIdx);
    }
}
