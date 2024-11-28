package com.spinner.www.vote.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.dto.VoteItemCreateDto;
import com.spinner.www.vote.io.DeleteVoteItemRequest;
import com.spinner.www.vote.io.UpdateVoteItemRequest;
import com.spinner.www.vote.io.VoteCreateRequest;
import com.spinner.www.vote.io.VoteItemCreateRequest;
import com.spinner.www.vote.repository.VoteItemRepo;
import com.spinner.www.vote.repository.VoteRepo;
import com.spinner.www.vote.repository.VoteUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private VoteRepo voteRepo;
    private VoteUserRepo voteUserRepo;
    private VoteItemRepo voteItemRepo;

    @Override
    public ResponseEntity<CommonResponse> insertVote(VoteCreateRequest voteCreateRequest) {
        VoteCreateDto voteCreateDto = voteCreateRequestToVoteCreateDto(voteCreateRequest);
        List<VoteItemCreateDto> voteItemCreateDtoList =
                voteItemCreateRequestListToVoteItemDtoList(voteCreateRequest.getVoteItemCreateRequestList());

        return null;
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

    /**
     * VoteCreateRequest -> VoteCreateDto 변환
     * @param voteCreateRequest VoteCreateRequest
     * @return VoteCreateDto
     */
    public VoteCreateDto voteCreateRequestToVoteCreateDto(VoteCreateRequest voteCreateRequest) {
        return VoteCreateDto.builder()
                .voteName(voteCreateRequest.getVoteName())
                .postIdx(voteCreateRequest.getPostIdx())
                .startDatetime(voteCreateRequest.getStartDatetime())
                .endDatetime(voteCreateRequest.getEndDatetime())
                .build();
    }

    /**
     * List<VoteItemCreateRequest> -> List<VoteItemCreateDto> 변환
     * @param voteItemCreateRequestList List<VoteItemCreateRequest>
     * @return List<VoteItemCreateDto>
     */
    public List<VoteItemCreateDto> voteItemCreateRequestListToVoteItemDtoList(List<VoteItemCreateRequest> voteItemCreateRequestList) {
        return voteItemCreateRequestList.stream()
                .map(voteItemCreateRequest -> VoteItemCreateDto.builder()
                        .voteItemName(voteItemCreateRequest.getVoteItemName())
                        .build())
                .collect(Collectors.toList());
    }
}
