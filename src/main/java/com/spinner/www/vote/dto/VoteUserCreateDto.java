package com.spinner.www.vote.dto;

import com.spinner.www.vote.io.VoteItemUserRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class VoteUserCreateDto {
    private Long voteIdx;
    private Long memberIdx;
}
