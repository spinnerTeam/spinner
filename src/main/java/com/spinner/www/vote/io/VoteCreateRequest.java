package com.spinner.www.vote.io;

import com.spinner.www.vote.entity.VoteStatus;
import com.spinner.www.vote.entity.VoteType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class VoteCreateRequest {

    @NotBlank(message = "게시물 idx는 필수 입력 조건입니다.")
    private Long boardIdx;

    @NotBlank(message = "투표 상태는 필수 입력 조건입니다. (ing: 투표 진행 중, multiple: 다중 선택 가능, end: 투표 완료")
    private VoteStatus voteStatus;

    @NotBlank(message = "투표 타입은 필수 입력 조건입니다. (community: 커뮤니티, study: 스터디)")
    private VoteType voteType;

    private List<VoteItemCreateRequest> voteItemCreateRequestList;
    private String voteName;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
}
