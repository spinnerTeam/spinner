package com.spinner.www.vote.mapper;

import com.spinner.www.vote.dto.*;
import com.spinner.www.vote.entity.Vote;
import com.spinner.www.vote.io.*;
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
                .voteIdx(voteUpdateRequest.getVoteIdx())
                .voteName(voteUpdateRequest.getVoteName())
                .build();
    }

    public VoteUpdateResponse toUpdateResponse(Vote vote, List<Long> voteItemIdxList) {
        return VoteUpdateResponse.builder()
                .voteIdx(vote.getId())
                .voteItemIdxList(voteItemIdxList)
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

    /**
     * VoteDeleteRequest -> VoteDto 변환
     * @param voteDeleteRequest VoteDeleteRequest
     * @return VoteDto
     */
    public VoteDto voteDeleteRequestToVoteDto(VoteDeleteRequest voteDeleteRequest) {
        return VoteDto.builder()
                .voteIdx(voteDeleteRequest.getVoteIdx())
                .build();
    }

    /**
     * List<VoteItemDeleteRequest> -> List<VoteItemDto> 변환
     * @param voteItemDeleteRequestList List<VoteItemDeleteRequest>
     * @return List<VoteItemDto>
     */
    public List<VoteItemDto> voteDeleteRequestToVoteItemDtoList(List<VoteItemDeleteRequest> voteItemDeleteRequestList) {
        return voteItemDeleteRequestList.stream()
                .map(v -> VoteItemDto.builder()
                        .voteItemIdx(v.getVoteItemIdx())
                        .build())
                .toList();
    }

    /**
     * DeleteResponse 반환
     * @param vote VoteItem
     * @param voteItemIdxList List<Long>
     * @return VoteDeleteResponse
     */
    public VoteDeleteResponse toDeleteResponse(Vote vote, List<Long> voteItemIdxList) {
        return VoteDeleteResponse.builder()
                .voteIdx(vote.getId())
                .voteItemIdxList(voteItemIdxList)
                .build();
    }

    /**
     * 삽입, 업데이트 등 결과 학인 IDX 반환 시 voteIDX 반환
     * @param voteIdx Long
     * @return VoteDto
     */
    public VoteDto voteIdxToVoteDto(Long voteIdx) {
        return VoteDto.builder()
                .voteIdx(voteIdx)
                .build();
    }

    /**
     * 투표 멤버 request -> dto
     * @param voteUserRequest VoteUserRequest
     * @return VoteUserCreateDto
     */
    public VoteUserCreateDto toVoteUserCreateDto(VoteUserRequest voteUserRequest) {
        return VoteUserCreateDto.builder()
                .voteIdx(voteUserRequest.getVoteIdx())
                .memberIdx(voteUserRequest.getMemberIdx())
                .build();
    }

    /**
     * 투표 유저의 투표 항목 다중 선택 변환
     * @param voteUserRequest VoteUserRequest
     * @return List<VoteItemUserCreateDto>
     */
    public List<VoteItemUserCreateDto> toVoteItemUserCreateDto(VoteUserRequest voteUserRequest) {
        return voteUserRequest.getVoteItemIdxList().stream()
                .map(vur -> VoteItemUserCreateDto.builder()
                        .voteItemIdx(vur.getVoteItemIdx())
                        .build())
                .toList();

    }

    /**
     * 투표 다중 선택 변환
     * @param vote Vote
     * @return VoteSelectResponse
     */
    public VoteSelectResponse toVoteSelectResponse(Vote vote) {
        return VoteSelectResponse.builder()
                .voteIdx(vote.getId())
                .voteName(vote.getVoteName())
                .voteItemSelectResponseList(vote.getVoteItems().stream()
                        .map(voteItem -> VoteItemSelectResponse.builder()
                                .voteItemIdx(voteItem.getId())
                                .voteItemName(voteItem.getVoteItemName())
                                .build())
                        .toList())
                .build();
    }

    public VoteSelectResponse createVoteSelectResponse(Vote vote, List<Vote> votes, boolean userVoted) {
        return VoteSelectResponse.builder()
                .voteIdx(vote.getId())
                .voteName(vote.getVoteName())
                .voteStatus(vote.getVoteStatus())
                .voteType(vote.getVoteType())
                .voteStatus(vote.getVoteStatus())
                .startDatetime(vote.getStartDatetime())
                .endDatetime(vote.getEndDatetime())
                .voteItemSelectResponseList(votes.stream()
                        .map(voteItem -> VoteItemSelectResponse.builder()
                                .voteItemIdx(voteItem.getId())
                                .voteItemName(voteItem.getVoteName())
                                .build())
                        .toList())
                .userVoted(userVoted)
                .build();
    }
}
