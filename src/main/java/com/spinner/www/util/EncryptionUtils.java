package com.spinner.www.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncryptionUtils(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * 비밀번호 암호화
     * @param password String
     * @return 암호화한 비밀번호
     */
    public String encrypt(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * 비밀번호 검증
     * @param prePassword String
     * @param password String
     * @return boolean
     */
    public boolean invalidatePassword(String prePassword,String password) {
        return bCryptPasswordEncoder.matches(prePassword, password);
    }
}
