package com.spinner.www.file.constants;

import java.util.Arrays;

public enum CommonFileCode {

    /**
     * 이미지 파일
     */
    IMAGE(10L, "image"),

    /**
     * 비디오 파일
     */
    VIDEO(11L, "video"),

    /**
     * 문서 파일
     */
    DOC(12L, "doc");

    private final Long code;
    private final String letter;

    /**
     * 생성자 생성
     * @param code    Long
     * @param letter String
     */
    CommonFileCode(Long code, String letter) {
        this.code = code;
        this.letter = letter;
    }

    static public Long getCode(String letter) {
        return Arrays.stream(CommonFileCode.values())
                .filter(fileCode -> fileCode.letter.equals(letter))
                .findFirst()
                .map(fileCode -> fileCode.code)
                .orElseThrow();
    }

    static public String getLetter(Long code) {
        return Arrays.stream(CommonFileCode.values())
                .filter(fileCode -> fileCode.code.equals(code))
                .findFirst()
                .map(fileCode -> fileCode.letter)
                .orElseThrow();
    }
}
