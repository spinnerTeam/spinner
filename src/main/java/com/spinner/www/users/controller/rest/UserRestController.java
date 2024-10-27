package com.spinner.www.users.controller.rest;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.users.dto.UserRequestDto;
import com.spinner.www.users.service.UserService;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/")
public class UserRestController {


    private final UserService userService;

    /**
     *  회원가입
     * @param userRequestDto UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @PostMapping("signup")
    public ResponseEntity<CommonResponse> invalidateEmail(@RequestBody UserRequestDto userRequestDto){
        return userService.insertUser(userRequestDto);
    }
}
