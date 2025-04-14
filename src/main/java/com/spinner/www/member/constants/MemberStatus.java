package com.spinner.www.member.constants;

public enum MemberStatus {

    ACTIVE("정상가입"),
    WITHDRAWN("탈퇴상태"),
    ANONYMIZED("탈퇴 후 개인정보 삭제 된 상태");

    private final String description;
    MemberStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
