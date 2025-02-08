package com.spinner.www.vote.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.util.ResponseVOUtils;
import com.spinner.www.vote.entity.VoteStatus;
import com.spinner.www.vote.io.*;
import com.spinner.www.vote.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "vote", description = "투표 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;
    
    /**
     * 투표 추가
     * @param voteCreateRequest VoteCreateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "투표 추가 API",
            description = "투표를 추가합니다. " +
            "투표를 생성할 때 복수 선택 여부를 선택할 수 있습니다. " +
            "투표 항목은 최대 다섯 개까지 추가 가능합니다. " +
            "투표가 생성된 이후, 투표가 생성된 IDX를 반환합니다. "
    )
    @Parameters({
            @Parameter(name = "boardIdx", description = "게시물 IDX"),
            @Parameter(name = "voteStatus", description = "[투표 상태] ing (투표 진행 중), multiple (다중 선택), end (투표 완료)"),
            @Parameter(name = "voteType", description = "[투표 타입] community, study"),
            @Parameter(name = "voteItemCreateRequestList", description = "투표 항목, List, voteItemName"),
            @Parameter(name = "voteName", description = "투표 이름"),
            @Parameter(name = "startDatetime", description = "투표 시작 일자"),
            @Parameter(name = "endDateTime", description = "투표 마감 일자")
    })
    @ApiResponses({
        @ApiResponse(content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "20000", description = "투표 생성 요청 성공"),
        @ApiResponse(responseCode = "41000", description = "투표 항목 미존재"),
        @ApiResponse(responseCode = "41001", description = "투표 항목 5개 초과")
    })
    @PostMapping
    public ResponseEntity<CommonResponse> insertVote(@Valid @RequestBody VoteCreateRequest voteCreateRequest) {

        // 투표 항목이 비어 있을 시
        if (voteCreateRequest.getVoteItemCreateRequestList().isEmpty()) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_VOTE_ITEM_COUNT_MIN_ONE), HttpStatus.BAD_REQUEST);
        }

        // 투표 항목 다섯 개 초과 시
        if (voteCreateRequest.getVoteItemCreateRequestList().size() > 5) {
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.INVALID_VOTE_ITEM_COUNT_MAX_FIVE), HttpStatus.BAD_REQUEST);
        }

        return voteService.insertVote(voteCreateRequest);
    }

    /**
     * 투표 리스트 조회
     * [ex] 게시물 클릭 후 투표 및 투표 항목 조회
     * @param boardIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping("/list/{boardIdx}")
    public ResponseEntity<CommonResponse> selectAllVotes(@PathVariable("boardIdx") Long boardIdx) {
        return voteService.selectAllVotes(boardIdx);
    }

    /**
     * 투표 단건 결과 조회
     * @param voteIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @GetMapping("/result/{voteIdx}")
    public ResponseEntity<CommonResponse> selectVoteResult(@PathVariable("voteIdx") Long voteIdx) {
        return voteService.selectVoteResult(voteIdx);
    }

    /**
     * 투표 및 항목 업데이트
     * @param voteUpdateRequest VoteUpdateRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PatchMapping
    public ResponseEntity<CommonResponse> updateVoteAndVoteItem(@RequestBody VoteUpdateRequest voteUpdateRequest) {
        return voteService.updateVoteItem(voteUpdateRequest);
    }

    /**
     * 투표 및 투표 항목 삭제
     * @param voteDeleteRequest DeleteVoteItemRequest
     * @return ResponseEntity<CommonResponse>
     */
    @DeleteMapping
    public ResponseEntity<CommonResponse> deleteVoteAndVoteItem(@RequestBody VoteDeleteRequest voteDeleteRequest) {
        return voteService.deleteVoteITem(voteDeleteRequest);
    }

    /**
     * [STUDY] 투표 즉시 마감
     * @param voteIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @PatchMapping("/{voteIdx}/end")
    public ResponseEntity<CommonResponse> endVoteItem(@PathVariable("voteIdx") Long voteIdx) {
        return voteService.endVote(voteIdx);
    }

    /**
     * 투표 참여
     * @param voteUserRequest VoteParticipateUserRequest
     * @return ResponseEntity<CommonResponse>
     */
    @PostMapping("/participate")
    public ResponseEntity<CommonResponse> selectVoteItem(@RequestBody VoteUserRequest voteUserRequest) {

        // 단수 선택의 경우, 여러 개의 투표 항목이 들어오면
        if (voteUserRequest.getVoteStatus() == VoteStatus.ing) {
           if (voteUserRequest.getVoteItemIdxList().size() > 1) {
               return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.NOT_MULTIPLE_VOTE), HttpStatus.BAD_REQUEST);
           }
        }

        // 마감된 투표의 경우
        if (voteUserRequest.getVoteStatus() == VoteStatus.end) {
                return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.END_VOTE), HttpStatus.BAD_REQUEST);
        }

        return voteService.selectVoteItem(voteUserRequest);
    }

}
