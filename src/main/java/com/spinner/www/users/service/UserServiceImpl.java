package com.spinner.www.users.service;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.users.dto.SessionInfo;
import com.spinner.www.users.io.UserLoginRequest;
import com.spinner.www.users.io.UserRequest;
import com.spinner.www.users.entity.Users;
import com.spinner.www.users.repository.UserRepo;
import com.spinner.www.util.EncryptionUtils;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    private EncryptionUtils encryptionUtils;
    private final HttpSession session;

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

    @Override
    public ResponseEntity<CommonResponse> loginUser(UserLoginRequest userLoginRequest) {

        // 이메일로 회원 객체 조회
        Users users = userRepo.findByEmail(userLoginRequest.getEmail());

        if (users == null) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.BAD_REQUEST);
        }

        boolean invalidatePassword = encryptionUtils.invalidatePassword(userLoginRequest.getUPassword(), users.getUPassword());

        if (invalidatePassword) {
            SessionInfo sessionInfo = new SessionInfo(users.getUIdx(), users.getUNickname(), users.getEmail());
            session.setAttribute("sessionInfo", sessionInfo);
        } else {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }
}
