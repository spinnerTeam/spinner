package com.spinner.www.users.dto;

import com.spinner.www.users.entity.Users;
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

    private Long uIdx;
    private String nickName;
    private String email;

    /**
     * 로그인 세션 등록
     * @param users Users
     */
    public void Login (Users users) {
        this.uIdx = users.getUIdx();
        this.nickName = users.getUNickname();
        this.email = users.getEmail();
    }
}
