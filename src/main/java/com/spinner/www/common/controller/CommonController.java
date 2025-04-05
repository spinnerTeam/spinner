package com.spinner.www.common.controller;

import com.spinner.www.common.dto.StudyTopicNameListDto;
import com.spinner.www.common.service.StudyTopicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "common", description = "공통 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/common")
public class CommonController {

    private final StudyTopicService studyTopicService;

    /**
     * 스터디 주제 조회
     * @return  List<StudyTopicNameListDto>
     */
    @GetMapping("/studyTopics")
    public List<StudyTopicNameListDto> getStudyTopicNameList(){
        return studyTopicService.getStudyTopicName();
    }
}
