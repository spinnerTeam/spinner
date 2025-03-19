package com.spinner.www.like.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spinner.www.common.io.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class LikeReplyResponse extends BaseResponse {
    @JsonProperty("isLiked")
    private boolean liked;
    private String nickname;
    private Long replyIdx;
}
