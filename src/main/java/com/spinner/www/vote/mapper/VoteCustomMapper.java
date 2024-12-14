package com.spinner.www.vote.mapper;

import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.dto.VoteItemCreateDto;
import com.spinner.www.vote.io.VoteCreateRequest;
import com.spinner.www.vote.io.VoteItemCreateRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VoteCustomMapper {

    /**
     * VoteCreateRequest -> VoteCreateDto 변환
     * @param voteCreateRequest VoteCreateRequest
     * @return VoteCreateDto
     */
    public VoteCreateDto voteCreateRequestToVoteCreateDto(VoteCreateRequest voteCreateRequest) {
        return VoteCreateDto.builder()
                .voteName(voteCreateRequest.getVoteName())
                .boardIdx(voteCreateRequest.getBoardIdx())
                .voteStatus(voteCreateRequest.getVoteStatus())
                .voteStartDatetime(voteCreateRequest.getStartDatetime())
                .voteEndDatetime(voteCreateRequest.getEndDatetime())
                .build();
    }

    /**
     * List<VoteItemCreateRequest> -> List<VoteItemCreateDto> 변환
     * @param voteItemCreateRequestList List<VoteItemCreateRequest>
     * @return List<VoteItemCreateDto>
     */
    public List<VoteItemCreateDto> voteItemCreateRequestListToVoteItemDtoList(List<VoteItemCreateRequest> voteItemCreateRequestList) {
        List<VoteItemCreateDto> voteItemCreateDtoList = new ArrayList<>();
        for (VoteItemCreateRequest voteItemCreateRequest : voteItemCreateRequestList) {
            VoteItemCreateDto build = VoteItemCreateDto.builder()
                    .voteItemName(voteItemCreateRequest.getVoteItemName())
                    .build();
            voteItemCreateDtoList.add(build);
        }
        return voteItemCreateDtoList;
    }
}
