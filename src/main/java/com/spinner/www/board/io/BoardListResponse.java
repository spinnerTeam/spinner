package com.spinner.www.board.io;

import com.spinner.www.common.io.BaseResponse;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
public class BoardListResponse extends BaseResponse {
    private Long idx;
    private String title;
    private String content;
    private String nickName;
    private long replyCount;

    public BoardListResponse(Long idx, String title, String content, String nickName, long replyCount, String createdAt, LocalDateTime createdDate, String modifiedAt, LocalDateTime modifiedDate) {
        super(createdAt, createdDate, modifiedAt, modifiedDate);
        this.idx = idx;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.replyCount = replyCount;
    }
}
