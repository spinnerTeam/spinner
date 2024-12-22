package com.spinner.www.member.constants;

/**
 * 약관동의
 * 필수 or 선택
 */
public enum ServiceTermsType {

    REQUIRED("필수"),   // 필수 동의
    OPTIONAL("선택");   // 선택 동의

    private final String description;

    ServiceTermsType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
