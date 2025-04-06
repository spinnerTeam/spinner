package com.spinner.www.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class EncryptionUtils {

    // BCryptPasswordEncoder 인스턴스를 static final로 관리
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // 생성자 감춤 (유틸리티 클래스)
    private EncryptionUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * 비밀번호 암호화
     * @param password String
     * @return 암호화된 비밀번호
     */
    public static String encrypt(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * 비밀번호 검증
     * @param prePassword 입력된 비밀번호
     * @param password 암호화된 비밀번호
     * @return boolean
     */
    public static boolean invalidatePassword(String prePassword, String password) {
        return bCryptPasswordEncoder.matches(prePassword, password);
    }

    /**
     * 비밀번호 형식 체크 (영문, 숫자 포함 8~20 자리)
     * @param password String
     */
    public static void checkPasswordFormat(String password) {
        String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,20}$";
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
    }
}
