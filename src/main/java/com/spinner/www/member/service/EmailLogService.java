package com.spinner.www.member.service;

import com.spinner.www.member.entity.EmailLog;

public interface EmailLogService {

    /**
     * 이메일 전송 로그
     * @param emailLog EmailLog
     */
    void saveEmailSend(EmailLog emailLog);
}
