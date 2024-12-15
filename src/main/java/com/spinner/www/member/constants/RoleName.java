package com.spinner.www.member.constants;

public enum RoleName {

    ADMIN("어드민"),
    BANNED_MEMBER("정지회원"),
    GENERAL_MEMBER("일반회원");

    private final String description;

    RoleName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
