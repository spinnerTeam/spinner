package com.spinner.www.vote.service;

import com.spinner.www.board.entity.Board;
import com.spinner.www.board.repository.BoardRepo;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.repository.MemberRepo;
import com.spinner.www.util.ResponseVOUtils;
import com.spinner.www.vote.dto.*;
import com.spinner.www.vote.entity.*;
import com.spinner.www.vote.io.*;
import com.spinner.www.vote.mapper.VoteCustomMapper;
import com.spinner.www.vote.mapper.VoteMapper;
import com.spinner.www.vote.repository.VoteItemRepo;
import com.spinner.www.vote.repository.VoteQueryRepo;
import com.spinner.www.vote.repository.VoteRepo;
import com.spinner.www.vote.repository.VoteUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {

    private final VoteRepo voteRepo;
    private final VoteUserRepo voteUserRepo;
    private final VoteItemRepo voteItemRepo;
    private final BoardRepo boardRepo;
    private final SessionInfo sessionInfo;
    private final VoteCustomMapper voteCustomMapper;
    private final MemberRepo memberRepo;
    private final VoteMapper voteMapper;
    private final VoteQueryRepo voteQueryRepo;

    /**
     * 투표 생성
     * @param voteCreateRequest VoteCreateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> insertVote(VoteCreateRequest voteCreateRequest) {

        VoteCreateDto voteCreateDto = voteCustomMapper.voteCreateRequestToVoteCreateDto(voteCreateRequest);

        // 연관된 포스트 조회
        Board board = boardRepo.getReferenceById(voteCreateDto.getBoardIdx());
        Vote vote = Vote.create(board, voteCreateDto);

        // 투표 생성 후 idx 반환
        voteRepo.save(vote);
        List<VoteItemCreateDto> voteItemCreateDtoList =
                voteCustomMapper.voteItemCreateRequestListToVoteItemDtoList(voteCreateRequest.getVoteItemCreateRequestList());

        // 투표 항목 저장
        insertVoteItems(vote, voteItemCreateDtoList);
        VoteResponse voteResponse = VoteResponse.builder().voteIdx(vote.getId()).build();
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(voteResponse), HttpStatus.OK);
    }

    /**
     * 투표 항목 생성
     * @param vote Vote
     * @param voteItemCreateDtoList List<VoteItemCreateDto>
     */
    @Transactional
    public void insertVoteItems(Vote vote, List<VoteItemCreateDto> voteItemCreateDtoList) {
        List<VoteItem> voteItemList = new ArrayList<>();
        for (VoteItemCreateDto voteItemCreateDto : voteItemCreateDtoList) {
            VoteItem voteItem = VoteItem.create(vote, voteItemCreateDto);
            voteItemList.add(voteItem);
            vote.addVoteItem(voteItem);
            voteItemList.add(voteItem);
        }
        voteItemRepo.saveAll(voteItemList);
    }

    /**
     * 투표 리스트 조회
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> selectAllVotes(Long boardIdx) {

        // 게시물 클릭 시
        // 커뮤니티는 투표 완료가 없으며, 투표한 사람만 결과 확인이 가능함
        // 투표 상태에 따라 기본 베이스가 변경됨 (ing, multiple 항목, end 결과)
        VoteSelectDto voteSelectDto = voteMapper.toVoteSelectDto(boardIdx);
        Board board = boardRepo.getReferenceById(voteSelectDto.getBoardIdx());
        List<Vote> votes = voteRepo.findVotesByBoard(board);

        // 투표 리스트가 비어 있으면
        if (votes.isEmpty()) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        // (투표 진행 중 && 투표 완료된 리스폰트) 리스트 결과값 반환
        List<Object> voteResponseList = new ArrayList<>();

        for (Vote vote : votes) {

            // (1) 투표가 진행 중이거나 중복 투표가 가능하면
            if (vote.getVoteStatus() == VoteStatus.ing || vote.getVoteStatus() == VoteStatus.multiple) {

                // 투표 진행 중 리스폰스
                VoteSelectResponse voteSelectResponse;

                // (1-1) 로그인 여부 확인
                Long memberIdx = sessionInfo.getMemberIdx();

                if (memberIdx != null) {
                    // (1-2) 로그인된 사용자 처리
                    Member member = memberRepo.getReferenceById(memberIdx);
                    VoteUser voteUser = voteUserRepo.findByMember(member);

                    if (voteUser != null) {
                        // (1-3) 유저가 투표에 참여했으면
                        voteSelectResponse = voteCustomMapper.createVoteSelectResponse(vote, votes, true);
                    } else {
                        // (1-4) 유저가 투표에 참여하지 않았으면
                        voteSelectResponse = voteCustomMapper.createVoteSelectResponse(vote, votes, false);
                    }
                    voteResponseList.add(voteSelectResponse);
                } else {
                    // (1-5) 비로그인 사용자 처리
                    voteSelectResponse = voteCustomMapper.createVoteSelectResponse(vote, votes, false);
                    voteResponseList.add(voteSelectResponse);
                }

            // (2) 투표가 완료됐으면
            } else {
                if (vote.getVoteType() == VoteType.community) {
                    // 투표 완료 리스폰스
                    VoteResultsCommunityResponse voteResultsCommunityResponse = voteQueryRepo.findVoteCommunityResultsByVote(vote);
                    voteResponseList.add(voteResultsCommunityResponse);
                } else if (vote.getVoteType() == VoteType.study) {
                    // [MEMO] 스터디일 경우, 참여자의 닉네임이 공개된다.
                    // 각 항목을 선택한 참여자가 보이며, 스터디 미참여 인원 또한 볼 수 있다.
                }

            }
        }

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(voteResponseList), HttpStatus.OK);
    }

    /**
     * 개인 투표 결과 반환
     * @param voteIdx Long
     * @return selectVoteResult
     */
    @Override
    public ResponseEntity<CommonResponse> selectVoteResult(Long voteIdx) {

        Vote vote = voteRepo.getReferenceById(voteIdx);

        // 유저가 로그인되어 있지 않은 경우
        if (sessionInfo.getMemberIdx() == null) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.VOTE_RESULT_NOT_ACCESS), HttpStatus.NOT_FOUND);
        }

        Member member = memberRepo.getReferenceById(sessionInfo.getMemberIdx());
        VoteUser voteUser = voteUserRepo.findByMember(member);

        // 커뮤니티의 경우, 유저 투표를 완료한 상태라면
        if (vote.getVoteType() == VoteType.community && voteUser != null) {
            VoteResultsCommunityResponse voteResultsCommunityResponse = voteQueryRepo.findVoteCommunityResultsByVote(vote);
            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(voteResultsCommunityResponse), HttpStatus.OK);

            // 스터디의 경우, 투표 마감이 된 상태라면
        } else if (vote.getVoteType() == VoteType.study && vote.getVoteStatus() == VoteStatus.end) {
            // [MEMO] 스터디일 경우, 참여자의 닉네임이 공개된다.
            // 각 항목을 선택한 참여자가 보이며, 스터디 미참여 인원 또한 볼 수 있다.
        }

        return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.VOTE_RESULT_NOT_ACCESS), HttpStatus.BAD_REQUEST);
    }

    /**
     * 투표 및 투표 항목 수정
     * @param voteUpdateRequest VoteUpdateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> updateVoteItem(VoteUpdateRequest voteUpdateRequest) {

        // 투표 수정
        VoteDto voteDto = voteCustomMapper.voteUpdateRequestToVoteDto(voteUpdateRequest);
        Member member = memberRepo.getReferenceById(sessionInfo.getMemberIdx());
        Vote vote = voteRepo.findById(voteDto.getVoteIdx()).orElseThrow(() -> new NullPointerException("Vote Idx를 찾을 수 없습니다."));

        // 로그인한 사람과 작성자가 일치하지 않을 시
        if (!Objects.equals(vote.getCreatedAt(), member.getMemberIdx())) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

        vote.update(voteDto);

        List<Long> voteItemIdResponse = new ArrayList<>();

        // 투표 항목 수정
        if (voteUpdateRequest.getVoteItemUpdateRequestList() != null && !voteUpdateRequest.getVoteItemUpdateRequestList().isEmpty()) {
            voteItemIdResponse = voteItemUpdate(voteUpdateRequest, voteItemIdResponse);
        }

        VoteUpdateResponse voteUpdateResponse = voteCustomMapper.toUpdateResponse(vote, voteItemIdResponse);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(voteUpdateResponse), HttpStatus.OK);
    }

    /**
     * 투표 항목 업데이트
     * @param voteUpdateRequest VoteUpdateRequest
     * @param voteItemIdResponse List<Long>
     */
    @Transactional
    public List<Long> voteItemUpdate(VoteUpdateRequest voteUpdateRequest, List<Long> voteItemIdResponse) {

        List<VoteItemDto> voteItemDto = voteCustomMapper.voteUpdateRequestToVotItemDto(voteUpdateRequest.getVoteItemUpdateRequestList());

        // UPDATE 배치 저장 리스트 생성
        List<VoteItem> voteItemsToUpdate = new ArrayList<>();

        for (VoteItemDto voteItemDtoItem : voteItemDto) {
            VoteItem voteItem = voteItemRepo.findById(voteItemDtoItem.getVoteItemIdx())
                    .orElseThrow(() -> new NullPointerException("VoteItem Idx를 찾을 수 없습니다."));
            voteItem.update(voteItemDtoItem);
            voteItemsToUpdate.add(voteItem);
            voteItemIdResponse.add(voteItemDtoItem.getVoteItemIdx());
        }

        voteItemRepo.saveAll(voteItemsToUpdate);
        return voteItemIdResponse;
    }

    /**
     * 투표 및 투표 항목 삭제
     * @param voteDeleteRequest VoteDeleteRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteVoteITem(VoteDeleteRequest voteDeleteRequest) {

        // 투표 삭제
        VoteDto voteDto = voteCustomMapper.voteDeleteRequestToVoteDto(voteDeleteRequest);
        Vote vote = voteRepo.findById(voteDto.getVoteIdx()).orElseThrow(() -> new NullPointerException("Vote Idx를 찾을 수 없습니다."));
        Member member = memberRepo.getReferenceById(sessionInfo.getMemberIdx());

        // 로그인한 사람과 작성자가 일치하지 않을 시
        if (!Objects.equals(vote.getCreatedAt(), member.getMemberIdx())) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

        vote.softDelete(voteDto);

        List<Long> voteItemIdResponse = new ArrayList<>();

        // 투표 항목 삭제
        if (voteDeleteRequest.getVoteItemDeleteRequestList() != null && !voteDeleteRequest.getVoteItemDeleteRequestList().isEmpty()) {
            voteItemIdResponse = voteItemDelete(voteDeleteRequest);
        }

        VoteDeleteResponse voteDeleteResponse = voteCustomMapper.toDeleteResponse(vote, voteItemIdResponse);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(voteDeleteResponse), HttpStatus.OK);
    }

    /**
     * 투표 항목 삭제
     * @param voteDeleteRequest VoteDeleteRequest
     * @return List<Long>
     */
    @Transactional
    public List<Long> voteItemDelete(VoteDeleteRequest voteDeleteRequest) {

        List<VoteItemDto> voteItemDto = voteCustomMapper.voteDeleteRequestToVoteItemDtoList(voteDeleteRequest.getVoteItemDeleteRequestList());

        //배치 삭제 리스트 생성
        List<VoteItem> voteItemsToDelete = new ArrayList<>();
        List<Long> voteItemIdResponse = new ArrayList<>();

        for (VoteItemDto voteItemDtoItem : voteItemDto) {
            VoteItem voteItem = voteItemRepo.findById(voteItemDtoItem.getVoteItemIdx())
                    .orElseThrow(() -> new NullPointerException("voteItem Idx를 찾을 수 없습니다."));
            voteItem.softDelete(voteItemDtoItem);
            voteItemsToDelete.add(voteItem);
            voteItemIdResponse.add(voteItemDtoItem.getVoteItemIdx());
        }

        voteItemRepo.saveAll(voteItemsToDelete);

        return voteItemIdResponse;
    }

    /**
     * 투표 마감 메서드
     * @param voteIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> endVote(Long voteIdx) {

        VoteDto voteDto = voteCustomMapper.voteIdxToVoteDto(voteIdx);
        Member member = memberRepo.getReferenceById(sessionInfo.getMemberIdx());
        Vote vote = voteRepo.findById(voteDto.getVoteIdx()).orElseThrow(() -> new NullPointerException("Vote Idx를 찾을 수 없습니다."));

        // 로그인한 사람과 작성자가 일치하지 않을 시
        if (!Objects.equals(vote.getCreatedAt(), member.getMemberIdx())) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);
        }

        vote.statusUpdate(voteDto);

        VoteResponse voteResponse = VoteResponse.builder().voteIdx(vote.getId()).build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(voteResponse), HttpStatus.OK);
    }

    /**
     * 투표 선택
     * @param voteUserRequest VoteUserRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> selectVoteItem(VoteUserRequest voteUserRequest) {

        // 로그인 사용자와 투표자가 일치하지 않는 경우
        if (!sessionInfo.getMemberIdx().equals(voteUserRequest.getMemberIdx())) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.VOTER_USER_MISMATCH), HttpStatus.BAD_REQUEST);
        }

        VoteUserCreateDto voteUserCreateDto = voteCustomMapper.toVoteUserCreateDto(voteUserRequest);

        // 필요한 객체 프록시 반환
        Member member = memberRepo.getReferenceById(voteUserCreateDto.getMemberIdx());
        Vote vote = voteRepo.getReferenceById(voteUserCreateDto.getVoteIdx());

        // 중복 투표 체크
        if (!voteQueryRepo.findVoteUser(member, vote)) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.VOTE_NOT_UPDATE), HttpStatus.BAD_REQUEST);
        }

        List<VoteItemUserCreateDto> voteItemUserCreateDtoList = voteCustomMapper.toVoteItemUserCreateDto(voteUserRequest);

        // [stream] voteItemUserCreateDtoList 타입인 VoteItemUserCreateDTO에서 voteItemIdx를 List로 만들고
        // voteItem 일괄 조회
        List<Long> voteItemIds = voteItemUserCreateDtoList.stream()
                .map(VoteItemUserCreateDto::getVoteItemIdx)
                .toList();
        List<VoteItem> voteItems = voteItemRepo.findAllById(voteItemIds);

        // 투표 항목과 투표가 일치하지 않으면
        if (!voteQueryRepo.findVote(vote, voteItemIds)) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.BAD_REQUEST);
        }

        // 조회된 VoteItems Map 변환
        // [stream] 리스트 타입인 voteItem 맵으로 전환, 키로 Id, 밸류로 voteItem
        Map<Long, VoteItem> voteItemMap = voteItems.stream()
                .collect(Collectors.toMap(VoteItem::getId, voteItem -> voteItem));

        List<Long> voteUserResponse = new ArrayList<>();

        // VoteUser 생성
        List<VoteUser> voteUsers = new ArrayList<>();
        for (VoteItemUserCreateDto voteItemDto : voteItemUserCreateDtoList) {
            VoteItem voteItem = voteItemMap.get(voteItemDto.getVoteItemIdx());
            if (voteItem == null) {
                throw new NullPointerException("투표 항목을 찾을 수 없습니다.");
            }
            VoteUser voteUser = VoteUser.create(member, vote, voteItem);
            voteUsers.add(voteUser);
            voteUserResponse.add(voteItemDto.getVoteItemIdx());
        }

        voteUserRepo.saveAll(voteUsers);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(voteUserResponse), HttpStatus.OK);
    }

}
