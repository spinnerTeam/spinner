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

    @NotBlank(message = "post idx는 필수로 들어와야 함")
    private Long postIdx;

    @NotBlank(message = "투표 상태는 필수로 들어와야 함")
    private VoteStatus voteStatus;

    private List<VoteItemCreateRequest> voteItemCreateRequestList;
    private String voteName;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
}
