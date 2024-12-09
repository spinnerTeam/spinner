package com.spinner.www.vote.io;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteItemCreateRequest {
    @NotBlank(message = "투표 항목 내용은 필수 입력 조건입니다.")
    private String voteItemName;
}
