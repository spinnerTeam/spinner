package com.spinner.www.reply.io;

import com.spinner.www.common.io.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@AllArgsConstructor
public class ReplyResponse extends BaseResponse {
    private Long idx;
    private String content;
    private String nickname;
    private List<ReplyResponse> childReplies;


    public ReplyResponse(Long idx, String content, String nickname) {
        this.idx = idx;
        this.content = content;
        this.nickname = nickname;
    }
}
