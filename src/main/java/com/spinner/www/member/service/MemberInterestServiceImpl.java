package com.spinner.www.member.service;

import com.spinner.www.common.entity.Menu;
import com.spinner.www.common.service.MenuService;
import com.spinner.www.member.dto.MemberInterestCreateDto;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.MemberInterest;
import com.spinner.www.member.io.MemberInterestRequest;
import com.spinner.www.member.io.MemberInterestResponse;
import com.spinner.www.member.repository.MemberInterestRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberInterestServiceImpl implements MemberInterestService {

    private final MemberInterestRepo memberInterestRepo;
    private final MenuService menuService;

    /**
     * MemberInterest 객체 생성
     * @param memberInterestCreateDto MemberInterestCreateDto
     */
    @Override
    public void insertMemberInterest(MemberInterestCreateDto memberInterestCreateDto){
        MemberInterest memberInterest = MemberInterest.insertMemberInterest(memberInterestCreateDto);
        memberInterestRepo.save(memberInterest);
    }

    /**
     * MemberInterest 객체 수정(선택 여부만 수정)
     * @param memberInterest MemberInterest
     * @param isSelected boolean
     */
    @Override
    public void updateMemberInterest(MemberInterest memberInterest, boolean isSelected) {
        memberInterest.updateMemberIsSelected(isSelected);
        memberInterestRepo.save(memberInterest);
    }

    /**
     * Member 객체를 기준으로 전체 MemberInterest 객체를 생성하거나 수정함
     * @param member Member
     * @param memberInterestRequests List<MemberInterestRequest>
     */
    @Override
    public void upsertMemberInterests(Member member, List<MemberInterestRequest> memberInterestRequests) {
        List<MemberInterest> memberInterests = getAllMemberInterest(member);
        Map<Long, MemberInterest> mapInterest = memberInterests.stream()
                .collect(Collectors.toMap(memberInterest -> memberInterest.getMenu().getMenuIdx(), memberInterest -> memberInterest));
        memberInterestRequests.forEach(memberInterestRequest -> {
            Long menuIdx = memberInterestRequest.getIdx();
            MemberInterest memberInterest = mapInterest.get(menuIdx);
            boolean isPreExisting = Objects.nonNull(memberInterest);
            boolean isSelected = memberInterestRequest.isSelected();

            if(isPreExisting){
                updateMemberInterest(memberInterest, isSelected);
            }
            else if(isSelected) {
                Menu menu = menuService.getMenuByMenuIdx(menuIdx).orElse(null);
                if(Objects.isNull(menu)) return;
                MemberInterestCreateDto memberInterestCreateDto = MemberInterestCreateDto.builder()
                        .menu(menu)
                        .member(member)
                        .build();
                insertMemberInterest(memberInterestCreateDto);
            }
        });
    }

    /**
     * Member 객체를 기준으로 MemberInterest 객체 조회
     * @param member Member
     * @return MemberInterest
     */
    @Override
    public List<MemberInterest> getAllMemberInterest(Member member) {
        return memberInterestRepo.findAllByMember(member);
    }

    /**
     * Member 객체를 기준으로 MemberInterestResponse 리스트 객체 리턴
     * @return List<MemberInterestResponse>
     */
    @Override
    public List<MemberInterestResponse> getAllMemberInterestResponses(Member member) {

        List<Menu> menus = menuService.getAllInterest();
        List<MemberInterest> interests = getAllMemberInterest(member);

        List<MemberInterestResponse> responses = new ArrayList<>();
        for (Menu menu : menus) {
            responses.add(MemberInterestResponse.builder()
                    .idx(menu.getMenuIdx())
                    .name(menu.getCommonCode().getCodeName())
                    .selected(interests.stream().anyMatch(interest -> interest.getMenu().equals(menu)&&interest.isMemberIsSelected()))
                    .build());
        }
        return responses;
    }
}


