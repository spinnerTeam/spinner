package com.spinner.www.board.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spinner.www.common.io.BaseResponse;
import com.spinner.www.reply.io.ReplyResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class BoardResponse extends BaseResponse {
    private Long idx;
    private String boardName;
    private String nickname;
    private String title;
    private String content;
    private List<ReplyResponse> replies;
    private Long likeCount;
    @JsonProperty("isLiked")
    private boolean liked;
    private Long hitCount;
    @JsonProperty("isBookmarked")
    private boolean bookmarked;
    private List<BoardFileResponse> files;

    public BoardResponse(Long idx, String boardName, String nickname, String title, String content, List<ReplyResponse> replies, Long likeCount, boolean isLiked, Long hitCount, boolean isBookmarked, List<BoardFileResponse> files, String createdAt, LocalDateTime createdDate, String modifiedAt, LocalDateTime modifiedDate) {
        super(createdAt, createdDate, modifiedAt, modifiedDate);
        this.idx = idx;
        this.boardName = boardName;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.replies = replies;
        this.likeCount = likeCount;
        this.liked = isLiked;
        this.hitCount = hitCount;
        this.bookmarked = isBookmarked;
        this.files = files;
    }
}
