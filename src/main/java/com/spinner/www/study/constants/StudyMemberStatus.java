package com.spinner.www.study.constants;

public enum StudyMemberStatus {

    WAITING("승인대기"),
    APPROVED("승인"),
    WITHDRAWN("탈퇴"),
    BLOCKED("차단"),
    FINISHED("종료")
    ;

    private final String description;
    StudyMemberStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
