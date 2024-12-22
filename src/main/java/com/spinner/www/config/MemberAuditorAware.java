package com.spinner.www.config;

import com.spinner.www.member.dto.SessionInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * baseEntity 에서 createAt , modifiedAt 회원 번호로 들어가게 설정
 */
@Component
@RequiredArgsConstructor
public class MemberAuditorAware implements AuditorAware<Long> {

    private final SessionInfo sessionInfo;

    @Override
    public Optional<Long> getCurrentAuditor() {

        return Optional.ofNullable(sessionInfo.getMemberIdx()).or(() -> Optional.of(-1L));
    }
}
