package com.spinner.www.board.io;

import com.spinner.www.common.io.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@Setter
public class BoardListResponse extends BaseResponse {
    private Long idx;
    private String boardName;
    private String title;
    private String content;
    private String nickName;
    private Long voteCount;
    private Long replyCount;
    private Long likeCount;
    private Long hitCount;

    public BoardListResponse(Long idx, String boardName, String title, String content, String nickName, Long voteCount, Long replyCount, Long likeCount, Long hitCount, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        super(createdDate, modifiedDate);
        this.idx = idx;
        this.boardName = boardName;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.voteCount = voteCount;
        this.replyCount = replyCount;
        this.likeCount = likeCount;
        this.hitCount = hitCount;
    }
}
