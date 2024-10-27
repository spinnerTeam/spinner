package com.spinner.www.users.service;


import com.spinner.www.common.CommonResponse;
import com.spinner.www.users.dto.UserRequestDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    /**
     *  회원가입
     * @param userRequestDto UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    ResponseEntity<CommonResponse> insertUser(UserRequestDto userRequestDto);
}
