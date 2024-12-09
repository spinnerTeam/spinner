package com.spinner.www.vote.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.post.entity.Post;
import com.spinner.www.post.repository.PostRepo;
import com.spinner.www.util.ResponseVOUtils;
import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.dto.VoteItemCreateDto;
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
                .orElseThrow(() -> new RuntimeException("Post Not Found"));

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

    @Override
    public ResponseEntity<CommonResponse> selectAllVotes(Long postIdx) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> selectVote(Long voteIdx) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> updateVoteItem(List<UpdateVoteItemRequest> voteItemUpdateList) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> deleteVoteITem(List<DeleteVoteItemRequest> voteItemDeleteList) {
        return null;
    }
}
