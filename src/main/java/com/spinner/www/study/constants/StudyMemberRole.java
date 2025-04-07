package com.spinner.www.study.constants;

public enum StudyMemberRole {

    LEADER("스터디장"),
    SUB_LEADER("부스터디장"),
    MEMBER("스터디원");
    private final String description;


    StudyMemberRole(String description){this.description = description;}
    public String getDescription() {
        return description;
    }
}
