package com.spinner.www.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SessionInfo implements Serializable {

    private Long memberIdx;
    private String memberNickName;
    private String memberEmail;

    /**
     * 로그인 세션 등록
     * @param users Users
     */
    public void Login (UserLoginDto users) {
        this.memberIdx = users.getMemberIdx();
        this.memberNickName = users.getMemberNickname();
        this.memberEmail = users.getMemberEmail();
    }

    /**
     * 로그아웃 세션 초기화
     * [note] redis 세션값 초기화 되지만 세션 ID는 만료시간까지 살아있음.
     */
    public void logout () {
        this.memberIdx = null;
        this.memberNickName = null;
        this.memberEmail = null;
    }
}
