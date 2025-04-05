package com.spinner.www.util;

import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.common.repository.StudyTopicQueryRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudyTopicCacheStorage {

    private final StudyTopicQueryRepo studyTopicQueryRepo;
    List<StudyTopic> studyTopics;

    @PostConstruct
    public void getUseStudyTopicMap(){
        this.studyTopics = studyTopicQueryRepo.findWithCommonCode(2, 1L);
    }

    public List<StudyTopic> getStudyTopics(){
        return studyTopics;
    }
}
