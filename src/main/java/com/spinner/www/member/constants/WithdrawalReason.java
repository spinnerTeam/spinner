package com.spinner.www.member.constants;

public enum WithdrawalReason {

    NO_STUDY("원하는 스터디가 없어요"),
    TOO_MANY_ERRORS("오류가 자주 생겨요"),
    WANT_NEW_ACCOUNT("다른 계정으로 다시 가입하고 싶어요"),
    OTHER("직접 입력");

    private final String description;
    WithdrawalReason(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
