package com.spinner.www.vote.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.post.entity.Post;
import com.spinner.www.post.repository.PostRepo;
import com.spinner.www.util.ResponseVOUtils;
import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.dto.VoteDto;
import com.spinner.www.vote.dto.VoteItemCreateDto;
import com.spinner.www.vote.dto.VoteItemDto;
import com.spinner.www.vote.entity.Vote;
import com.spinner.www.vote.entity.VoteItem;
import com.spinner.www.vote.io.*;
import com.spinner.www.vote.mapper.VoteCustomMapper;
import com.spinner.www.vote.repository.VoteItemRepo;
import com.spinner.www.vote.repository.VoteRepo;
import com.spinner.www.vote.repository.VoteUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {

    private final VoteRepo voteRepo;
    private final VoteUserRepo voteUserRepo;
    private final VoteItemRepo voteItemRepo;
    private final PostRepo postRepo;
    private final VoteCustomMapper voteCustomMapper;

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
        Post post = postRepo.findById(voteCreateDto.getPostIdx())
                .orElseThrow(() -> new NullPointerException("투표와 연관된 포스트 Idx를 찾을 수 없습니다."));

        // 투표 엔티티 생성
        Vote vote = Vote.create(post, voteCreateDto);

        // 투표 생성 후 idx 반환
        voteRepo.save(vote);
        List<VoteItemCreateDto> voteItemCreateDtoList =
                voteCustomMapper.voteItemCreateRequestListToVoteItemDtoList(voteCreateRequest.getVoteItemCreateRequestList());

        // 투표 항목 저장
        insertVoteItems(vote, voteItemCreateDtoList);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
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
            voteItemList.add(VoteItem.create(vote, voteItemCreateDto));
        }
        voteItemRepo.saveAll(voteItemList);
    }

    /**
     * 투표 리스트 조회
     * @param postIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> selectAllVotes(Long postIdx) {

        //List<Vote> votes = voteRepo.findByPost(postIdx);
        //List<Vote> voteItemList = voteRepo.findBy

        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> selectVote(Long voteIdx) {
        return null;
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
        Vote vote = voteRepo.findById(voteDto.getVoteId()).orElseThrow(() -> new NullPointerException("Vote Idx를 찾을 수 없습니다."));
        vote.update(voteDto);

        List<Long> voteItemIdResponse = new ArrayList<>();

        // 투표 항목 수정
        if (voteUpdateRequest.getVoteItemUpdateRequestList() != null && !voteUpdateRequest.getVoteItemUpdateRequestList().isEmpty()) {
            List<VoteItemDto> voteItemDto = voteCustomMapper.voteUpdateRequestToVotItemDto(voteUpdateRequest.getVoteItemUpdateRequestList());

            // UPDATE 배치 저장 리스트 생성
            List<VoteItem> voteItemsToUpdate = new ArrayList<>();

            for (VoteItemDto voteItemDtoItem : voteItemDto) {
                VoteItem voteItem = voteItemRepo.findById(voteItemDtoItem.getVoteItemIdx())
                        .orElseThrow(() -> new NullPointerException("VoteItemIdx를 찾을 수 없습니다."));
                voteItem.update(voteItemDtoItem);
                voteItemsToUpdate.add(voteItem);
                voteItemIdResponse.add(voteItemDtoItem.getVoteItemIdx());
            }

            voteItemRepo.saveAll(voteItemsToUpdate);
        }

        VoteUpdateResponse voteUpdateResponse = voteCustomMapper.voteToVoteUpdateResponse(vote, voteItemIdResponse);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(voteUpdateResponse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommonResponse> deleteVoteITem(List<DeleteVoteItemRequest> voteItemDeleteList) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> endVote(Long voteIdx) {
        return null;
    }
}
