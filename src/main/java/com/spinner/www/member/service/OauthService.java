package com.spinner.www.member.service;


import com.spinner.www.member.repository.SocialRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OauthService extends DefaultOAuth2UserService {

    private final SocialRepo socialRepo;

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
        }

        // sub가 있는지 없는지
        boolean hasSocialSub = socialRepo.existsBySocialNum(sub);

        // 있으면 로그인 처리
        if(hasSocialSub){

        }else{
            // 없으면 회원가입으로
        }

        return user;
    }
}
