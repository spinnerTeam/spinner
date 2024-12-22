package com.spinner.www.vote.io;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class VoteItemUpdateRequest {
    private Long voteItemIdx;
    private String voteItemName;
    private String voteItemStatus;
}
