package com.spinner.www.users.service;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.users.dto.SessionInfo;
import com.spinner.www.users.dto.UserLoginDto;
import com.spinner.www.users.io.UserLoginRequest;
import com.spinner.www.users.io.UserRequest;
import com.spinner.www.users.entity.Users;
import com.spinner.www.users.mapper.UserMapper;
import com.spinner.www.users.repository.UserRepo;
import com.spinner.www.util.EncryptionUtils;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    // 토큰 만료 시간
    private static final int DEFAULT_ACCESS_EXPIRATION_MINUTES = 30;
    // 토큰 만료 일
    private static final int DEFAULT_REFRESH_EXPIRATION_DAYS = 7;

    private final UserRepo userRepo;
    private final EncryptionUtils encryptionUtils;
    private final SessionInfo sessionInfo;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final HttpServletResponse response;

    /**
     * user 이메일 중복검사
     * @param uEmail String
     * @return 조회한 결과 Boolean
     */
    private boolean isEmailInvalid (String uEmail){
        return userRepo.existsByEmail(uEmail);
    }

    /**
     * 회원가입
     * @param userRequest UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @Override
    public ResponseEntity<CommonResponse> insertUser(UserRequest userRequest) {

        // 이메일 중복검사
        boolean isEmailInvalid = isEmailInvalid(userRequest.getEmail());

        if (isEmailInvalid) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DUPLICATE),HttpStatus.CONFLICT);
        }

        // 비밀번호 암호화
        String pw = encryptionUtils.encrypt(userRequest.getUPassword());

        Users user = Users.builder()
                .email(userRequest.getEmail())
                .uName(userRequest.getUName())
                .uPassword(pw)
                .uBirth(userRequest.getUBirth())
                .uNickname(userRequest.getUNickname())
                .build();
        userRepo.save(user);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.CREATED);
    }

    /**
     * users > userLoginDto 변환
     * @param email String
     * @return userLoginDto
     */
    public UserLoginDto getUserLoginDto(String email) {
        Users user = userRepo.findByEmail(email);
        return userMapper.usersToUserLoginDTO(user);
    }

    /**
     *  로그인
     * @param userLoginRequest UserLoginDto 로그인 요청 데이터
     * @return ResponseEntity<CommonResponse> 로그인 결과
     */
    @Override
    public ResponseEntity<CommonResponse> loginUser(UserLoginRequest userLoginRequest) {

        // 이메일로 회원 객체 조회
        UserLoginDto userLoginDto = getUserLoginDto(userLoginRequest.getEmail());

        if (userLoginDto == null)  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.BAD_REQUEST);
        }

        boolean invalidatePassword = encryptionUtils.invalidatePassword(userLoginRequest.getUPassword(), userLoginDto.getUPassword());

        if(!invalidatePassword)  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.BAD_REQUEST);
        }

        // acessToken 생성
        String accessToken =
                tokenService.makeToken(new Date(System.currentTimeMillis() + Duration.ofMinutes(DEFAULT_ACCESS_EXPIRATION_MINUTES).toMillis()), userLoginDto);
        userLoginDto.setAcessToken(accessToken);
        // refreshToken 생성
        String refreshToken =
                tokenService.makeToken(new Date(System.currentTimeMillis() + Duration.ofDays(DEFAULT_REFRESH_EXPIRATION_DAYS).toMillis()), userLoginDto);

        // refreshToken redis에 저장
        tokenService.saveRefreshToken(String.valueOf(userLoginDto.getUIdx()), refreshToken, DEFAULT_REFRESH_EXPIRATION_DAYS , TimeUnit.DAYS);
        // refreshToken http쿠키에 저장
        setRefreshTokenCookie(response, refreshToken, DEFAULT_REFRESH_EXPIRATION_DAYS);

        sessionInfo.Login(userLoginDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(accessToken), HttpStatus.OK);
    }

    /**
     * 쿠키에 refreshToken 저장
     * @param response HttpServletResponse
     * @param refreshToken String
     * @param expiryDate int
     */
    @Override
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken, int expiryDate) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        // 추후 https로 변경 하고 수정
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(expiryDate);

        response.addCookie(cookie);
    }
}
