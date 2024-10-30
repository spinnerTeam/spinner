package com.spinner.www.users.controller.rest;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.users.dto.SessionInfo;
import com.spinner.www.users.dto.UserLoginDto;
import com.spinner.www.users.dto.UserRequestDto;
import com.spinner.www.users.service.UserService;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/")
public class UserRestController {


    private final UserService userService;
    private final HttpSession session;

    /**
     *  회원가입
     * @param userRequestDto UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @PostMapping("signup")
    public ResponseEntity<CommonResponse> invalidateEmail(@RequestBody UserRequestDto userRequestDto){
        return userService.insertUser(userRequestDto);
    }

    /**
     * 로그인
     * @param userLoginDto UserLoginDto 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    @PostMapping("login")
    public ResponseEntity<CommonResponse> loginUser(@RequestBody UserLoginDto userLoginDto){
        return userService.loginUser(userLoginDto);
    }

    /**
     * 세션확인 테스트
     * @return
     */
    @GetMapping("session")
    public SessionInfo getSessionInfo() {
        return (SessionInfo) session.getAttribute("sessionInfo");
    }
}
