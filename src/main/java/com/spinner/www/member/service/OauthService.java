package com.spinner.www.member.service;


import com.spinner.www.member.entity.Social;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthService extends DefaultOAuth2UserService {

    private final SocialService socialService;
    private final RedisService redisService;
    private final HttpServletRequest request;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest){

        String clientId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        OAuth2User user = super.loadUser(oAuth2UserRequest);
        Map<String, Object> attributes = user.getAttributes();
        String sub = "";

        switch (clientId){
            case "google":
                sub = attributes.get("sub").toString();
                break;
            case "kakao" :
                sub = attributes.get("id").toString();
                break;
            case "naver" :
                @SuppressWarnings("unchecked")
                Map<String, Object> res = (Map<String, Object>) attributes.get("response");
                sub = res.get("id").toString();
                break;
            default:
                throw new IllegalArgumentException("지원되지 않는 소셜 로그인입니다: " + clientId);
        }

        HttpSession session = request.getSession(false);
        session.invalidate();

        // sub 가 있는지 없는지
        Social social = socialService.getSocial(sub);
        String email = redisService.getValue("email");

        if (social != null)  {
            socialService.loginSocial(social);
        } else {
            // 자사 회원이면서 소셜 연동 하려면
            if(email != null){
                socialService.joinSocialMember(sub, email, clientId);
            } else {
                // 회원가입으로
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "가입된 회원이 아닙니다.");
            }
        }
        return user;
    }
}
