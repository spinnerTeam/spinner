package com.spinner.www.util;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * // 비밀번호가 영문, 숫자 포함 8~20 자리인지 체크
     * @param password String
     * @return 검증 결과
     */
    public void  checkPasswordFormat(String password){
        String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";
        if(!password.matches(PASSWORD_PATTERN))  {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
    }
}
