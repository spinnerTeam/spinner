package com.spinner.www.common.service;

import com.spinner.www.common.dto.StudyTopicNameListDto;
import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.common.repository.StudyTopicRepo;
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

    /**
     * menuIdx로 Menu 조회
     * @param menuIdx Long
     * @return Menu
     */
    @Override
    public Optional<StudyTopic> getStudyTopicByStudyTopicIdx(Long menuIdx) {
        return studyTopicRepo.findByStudyTopicIdx(menuIdx);
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
}
