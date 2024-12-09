package com.spinner.www.vote.mapper;

import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.dto.VoteDto;
import com.spinner.www.vote.dto.VoteItemCreateDto;
import com.spinner.www.vote.dto.VoteItemDto;
import com.spinner.www.vote.entity.Vote;
import com.spinner.www.vote.entity.VoteItem;
import com.spinner.www.vote.io.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
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
                .postIdx(voteCreateRequest.getPostIdx())
                .voteStatus(voteCreateRequest.getVoteStatus())
                .voteType(voteCreateRequest.getVoteType())
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

    /**
     * VoteUpdateRequest -> VoteDto 뱐환
     * @param voteUpdateRequest VoteUpdateRequest
     * @return VoteDto
     */
    public VoteDto voteUpdateRequestToVoteDto(VoteUpdateRequest voteUpdateRequest) {
        return VoteDto.builder()
                .voteId(voteUpdateRequest.getVoteIdx())
                .voteName(voteUpdateRequest.getVoteName())
                .build();
    }

    public VoteUpdateResponse voteToVoteUpdateResponse(Vote vote, List<Long> voteItemIdxList) {
        return VoteUpdateResponse.builder()
                .voteId(vote.getId())
                .voteItemId(voteItemIdxList)
                .build();
    }

    /**
     * VoteItemUpdateRequestList -> List<VoteItemDto> 변환
     * @param voteItemUpdateRequestList VoteItemUpdateRequest>
     * @return List<VoteItemDto>
     */
    public List<VoteItemDto> voteUpdateRequestToVotItemDto(List<VoteItemUpdateRequest> voteItemUpdateRequestList) {
        return voteItemUpdateRequestList.stream()
                .map(v -> VoteItemDto.builder()
                        .voteItemIdx(v.getVoteItemIdx())
                        .voteItemName(v.getVoteItemName())
                        .voteItemStatus(v.getVoteItemStatus())
                        .build())
                .toList();
    }

}
