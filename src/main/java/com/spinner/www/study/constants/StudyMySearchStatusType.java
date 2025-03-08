package com.spinner.www.study.constants;

import lombok.Getter;

@Getter
public enum StudyMySearchStatusType {
    END(StudyStatusType.END, StudyMemberStatusType.JOIN),
    ING(StudyStatusType.ING, StudyMemberStatusType.JOIN),
    WAITING(StudyStatusType.ING, StudyMemberStatusType.WAITING);

    private final StudyStatusType studyStatusType;
    private final StudyMemberStatusType studyMemberStatusType;

    StudyMySearchStatusType(StudyStatusType studyStatusType, StudyMemberStatusType studyMemberStatusType) {
        this.studyStatusType = studyStatusType;
        this.studyMemberStatusType = studyMemberStatusType;
    }
}