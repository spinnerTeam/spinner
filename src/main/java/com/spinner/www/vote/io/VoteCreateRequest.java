package com.spinner.www.vote.io;

import com.spinner.www.vote.entity.VoteStatus;
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

    @NotBlank(message = "투표 상태는 필수 입력 조건입니다.")
    private VoteStatus voteStatus;

    private List<VoteItemCreateRequest> voteItemCreateRequestList;
    private String voteName;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
}
