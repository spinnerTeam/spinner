package com.spinner.www.member.service;

import com.spinner.www.member.dto.MemberInterestCreateDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.MemberInterest;
import com.spinner.www.member.io.MemberInterestRequest;
import com.spinner.www.member.io.MemberInterestResponse;

import java.util.List;

public interface MemberInterestService {

    /**
     * MemberInterest 객체 생성
     * @param memberInterestCreateDto MemberInterestCreateDto
     */
    void insertMemberInterest(MemberInterestCreateDto memberInterestCreateDto);

    /**
     * MemberInterest 객체 수정(선택 여부만 수정)
     * @param memberInterest MemberInterest
     * @param isSelected boolean
     */
    void updateMemberInterest(MemberInterest memberInterest, boolean isSelected);

    /**
     * Member 객체를 기준으로 전체 MemberInterest 객체를 생성하거나 수정함
     * @param member Member
     * @param memberInterestRequests List<MemberInterestRequest>
     */
    void upsertMemberInterests(Member member, List<MemberInterestRequest> memberInterestRequests);
    /**
     * Member 객체를 기준으로 MemberInterest 리스트 객체 조회
     * @param member Member
     * @return MemberInterest
     */
    List<MemberInterest> getAllMemberInterest(Member member);

    /**
     * Member 객체를 기준으로 MemberInterestResponse 리스트 객체 리턴
     * @param member Member
     * @return List<MemberInterestResponse>
     */
    List<MemberInterestResponse> getAllMemberInterestResponses(Member member);

}
