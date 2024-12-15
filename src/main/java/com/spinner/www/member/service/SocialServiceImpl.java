package com.spinner.www.member.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.member.constants.SocialType;
import com.spinner.www.member.dto.MemberSessionDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.Social;
import com.spinner.www.member.mapper.MemberMapper;
import com.spinner.www.member.repository.SocialRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialServiceImpl implements SocialService{

    private final SocialRepo socialRepo;
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    /**
     * sub 로 조회
     * @param socialNum String
     * @return social
     */
    @Override
    public Social getSocial(String socialNum) {
        return socialRepo.findBySocialNum(socialNum);
    }

    /**
     * 소셜로그인 처리
     * @param social Social
     * @return 소셜 로그인 결과
     */
    @Override
    public ResponseEntity<CommonResponse> loginSocial(Social social) {
        Member member = social.getMember();
        MemberSessionDto memberSessionDto = memberMapper.memberToMemberSessionDTO(member);
        memberService.makeLoginToken(member, memberSessionDto);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(memberSessionDto.getAcessToken()), HttpStatus.OK);
    }

    /**
     * 소셜 연동
     * @param socialNum String
     * @param memberEmail String
     * @param clientId String
     * @return 소셜 연동 결과
     */
    @Override
    public ResponseEntity<CommonResponse> joinSocialMember(String socialNum, String memberEmail, String clientId) {

        Member member = memberService.getMember(memberEmail);
        SocialType socialType = SocialType.valueOf(clientId.toUpperCase());
        Social social = Social.builder()
                .member(member)
                .socialtype(socialType)
                .socialNum(socialNum)
                .build();
        socialRepo.save(social);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(clientId), HttpStatus.OK);
    }
}
