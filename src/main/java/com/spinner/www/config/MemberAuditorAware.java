package com.spinner.www.config;

import com.spinner.www.member.dto.SessionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * baseEntity 에서 createAt , modifiedAt 회원 번호로 들어가게 설정
 */
@Component
@RequiredArgsConstructor
public class MemberAuditorAware implements AuditorAware<Long> {


    private final ObjectProvider<SessionInfo> sessionInfoProvider;

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            SessionInfo sessionInfo = sessionInfoProvider.getIfAvailable();
            // session 없으면 null return
            return Optional.ofNullable(sessionInfo != null ? sessionInfo.getMemberIdx() : null);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
