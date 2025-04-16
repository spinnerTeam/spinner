package com.spinner.www.board.constants;

import java.util.Arrays;

public enum CommonBoardCode {

    /**
     * 공부인증 게시판
     */
    VERIFY(7L, "verify"),

    /**
     * 자유 게시판
     */
    FREE(8L, "free"),

    /**
     * 자유 게시판
     */
    NOTICE(13L, "notice");

    private final Long code;
    private final String letter;

    /**
     * 생성자 생성
     * @param code    Long
     * @param letter String
     */
    CommonBoardCode(Long code, String letter) {
        this.code = code;
        this.letter = letter;
    }

    static public Long getCode(String letter) {
        return Arrays.stream(CommonBoardCode.values())
                .filter(boardCode -> boardCode.letter.equals(letter))
                .findFirst()
                .map(boardCode -> boardCode.code)
                .orElseThrow();
    }

    static public String getLetter(String code) {
        return Arrays.stream(CommonBoardCode.values())
                .filter(boardCode -> boardCode.code.equals(code))
                .findFirst()
                .map(boardCode -> boardCode.letter)
                .orElseThrow();
    }
}
