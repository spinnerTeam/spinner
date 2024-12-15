package com.spinner.www.member.service;

import com.spinner.www.member.entity.EmailLog;
import com.spinner.www.member.repository.EmailLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailLogServiceImpl implements EmailLogService {

    private final EmailLogRepo emailLogRepo;

    /**
     * 이메일 전송 로그
     * @param emailLog EmailLog
     */
    @Override
    public void saveEmailSend(EmailLog emailLog) {
        emailLogRepo.save(emailLog);
    }
}
