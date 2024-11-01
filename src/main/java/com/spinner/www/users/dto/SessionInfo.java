package com.spinner.www.users.dto;

import com.spinner.www.users.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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
