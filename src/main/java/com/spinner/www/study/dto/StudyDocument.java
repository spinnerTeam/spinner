package com.spinner.www.study.dto;


public record StudyDocument(
        Long studyIdx,
        String studyName,
        String studyInfo,
        String studyTopic,
        Integer studyCount
) {
}
