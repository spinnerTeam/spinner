package com.spinner.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * 소셜 로그인 config
 */
@Configuration
public class Oauth2ClientConfig {

    private final String baseUrl = "http://127.0.0.1:3030";

    @Bean
    public ClientRegistrationRepository registrationRepository() {
        return new InMemoryClientRegistrationRepository(
                kakaoRegistration(),
                googleRegistration(),
                naverRegistration()
        );
    }

    /**
     * 카카오 로그인
     * @return
     */
    private ClientRegistration kakaoRegistration(){
        return ClientRegistration.withRegistrationId("kakao")
                .clientId("24d026429569451a864b0e4e8a642107")
                .redirectUri(baseUrl + "/login/oauth2/code/kakao")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile_nickname")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .clientName("kakao")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .build();
    }

    /**
     * 구글 로그인
     * @return
     */
    private ClientRegistration googleRegistration(){
        return ClientRegistration.withRegistrationId("google")
                .clientId("765094755420-9pg8ggtl6s5e0ifrigmf9uq0388hsa8d.apps.googleusercontent.com")
                .clientSecret("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")
                .scope("email")
                .redirectUri(baseUrl + "/login/oauth2/code/google")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .tokenUri("https://oauth2.googleapis.com/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName("sub")
                .build();
    }

    /**
     * 네이버 로그인
     * @return
     */
    private ClientRegistration naverRegistration(){
        return ClientRegistration.withRegistrationId("naver")
                .clientId("YC5T_QvMPdgBkk01GDoD")
                .clientSecret("GFW2WV4AD5")
                .scope("name,email")
                .redirectUri(baseUrl + "/login/oauth2/code/naver")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("https://nid.naver.com/oauth2.0/authorize")
                .userInfoUri("https://openapi.naver.com/v1/nid/me")
                .userNameAttributeName("response")
                .tokenUri("https://nid.naver.com/oauth2.0/token")
                .clientName("naver")
                .build();

    }
}
