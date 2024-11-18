package com.spinner.www.member.service;


import com.spinner.www.common.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.entity.EmailTemplate;
import com.spinner.www.member.io.EmailAuthRequest;
import com.spinner.www.member.repository.EmailTemplateRepo;
import com.spinner.www.util.EmailTemplateCacheStorage;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService{

    private static final int DEFAULT_AUTHCODE_MINUTES = 5;

    private final JavaMailSender javaMailSender;
    private final EmailTemplateRepo emailTemplateRepo;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailTemplateCacheStorage emailTemplateCacheStorage;

    /**
     * 6자리 랜덤 숫자 (인증코드)
     * @return authCode String
     */
    @Override
    public String createAuthCode() {
        StringBuilder authCode = new StringBuilder();
        Random random = new Random();
        for (int i =0; i< 6; i++){
            authCode.append(random.nextInt(10));
        }
        return authCode.toString();
    }


    /**
     * 인증 메일 전송
     * @param toEmail 수신자 이메일 주소
     * @param type 이메일 템플릿 유형
     * @return ResponseEntity<CommonResponse> 이메일 전송 결과
     */
    @Override
    public ResponseEntity<CommonResponse> sendEmail(String toEmail, String type) {
        // 인증 코드 생성
        String authCode = createAuthCode();
        // 이메일 템플릿 조회
        EmailTemplate emailTemplate = emailTemplateCacheStorage.getEmailTemplate(type);
        if (emailTemplate == null)  {
            throw new IllegalArgumentException("유효하지 않은 이메일 템플릿 유형입니다: " + type);
        }

        // 이메일 본문
        String body = emailTemplate.getEmailTemplateBody().replace("{authCode}", authCode);

        // 인증 코드 Redis에 저장
        saveAuthCode(toEmail, authCode, DEFAULT_AUTHCODE_MINUTES, TimeUnit.MINUTES);

        try {
            // 이메일 메시지
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(new InternetAddress("finalgolfteam@gmail.com", "스피너팀"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(emailTemplate.getEmailTemplateTitle());
            message.setText(body, "UTF-8");

            // 이메일 발송
            javaMailSender.send(message);
            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(authCode), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 레디스에 인증코드 저장
     * @param email String
     * @param authCode String
     * @param minutes int
     * @param timeUnit TimeUnit
     */
    @Override
    public void saveAuthCode(String email, String authCode, int minutes , TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(email, authCode, minutes, timeUnit);
    }

    /**
     * 이메일 인증코드 검증
     * @param emailAuthRequest EmailAuthRequest
     * @return ResponseEntity<CommonResponse> 인증코드 비교 결과
     */
    @Override
    public ResponseEntity<CommonResponse> invalidateAuthCode(EmailAuthRequest emailAuthRequest) {
        String authCode = redisTemplate.opsForValue().get(emailAuthRequest.getEmail());
        if (emailAuthRequest.getAuthCode() == null || emailAuthRequest.getAuthCode().isEmpty())  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(1002, "인증 코드를 적어주세요"),HttpStatus.BAD_REQUEST);
        }
        if (authCode == null || !authCode.equals(emailAuthRequest.getAuthCode()))  {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(1001, "인증 코드가 유효하지 않습니다."),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse("인증이 완료되었습니다."), HttpStatus.OK);
    }

}
