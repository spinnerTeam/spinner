package com.spinner.www.users.service;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.users.dto.UserRequestDto;
import com.spinner.www.users.entity.Users;
import com.spinner.www.users.repository.UserRepo;
import com.spinner.www.util.EncryptionUtils;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    @Autowired
    private EncryptionUtils encryptionUtils;

    /**
     * user 이메일 중복검사
     * @param uEmail String
     * @return 조회한 결과 Boolean
     */
    private boolean isEmailInvalid(String uEmail){
        return userRepo.existsByUEmail(uEmail);
    }

    /**
     *  회원가입
     * @param userRequestDto UserRequestDto 회원가입 요청 데이터
     * @return ResponseEntity<CommonResponse> 회원가입 결과
     */
    @Override
    public ResponseEntity<CommonResponse> insertUser(UserRequestDto userRequestDto) {

        // 이메일 중복검사
        boolean isEmailInvalid = isEmailInvalid(userRequestDto.getUEmail());
        if(isEmailInvalid){
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DUPLICATE),HttpStatus.CONFLICT);
        }

        // 비밀번호 암호화
        String pw = encryptionUtils.encrypt(userRequestDto.getUPw());
        Users user = Users.builder()
                .uEmail(userRequestDto.getUEmail())
                .uName(userRequestDto.getUName())
                .uPw(pw)
                .uBirth(userRequestDto.getUBirth())
                .uNickname(userRequestDto.getUNickname())
                .build();
        userRepo.save(user);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.CREATED);
    }
}
