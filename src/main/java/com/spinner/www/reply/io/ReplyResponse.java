package com.spinner.www.reply.io;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spinner.www.common.io.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Getter
@SuperBuilder
@AllArgsConstructor
public class ReplyResponse extends BaseResponse {
    private Long idx;
    private String content;
    private String nickname;
    private List<ReplyResponse> childReplies;
    private Long likeCount;

    @JsonProperty("isLiked")
    private boolean isLiked;

    @JsonIgnore
    private boolean liked;

    public ReplyResponse(Long idx, String content, String nickname) {
        this.idx = idx;
        this.content = content;
        this.nickname = nickname;
    }

    public ReplyResponse(Long idx, String content, String nickname, Long likeCount, boolean isLiked) {
        this.idx = idx;
        this.content = content;
        this.nickname = nickname;
        this.likeCount = Objects.isNull(likeCount) ? 0 : likeCount;
        this.isLiked = isLiked;
    }
}
