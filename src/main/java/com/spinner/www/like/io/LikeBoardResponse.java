package com.spinner.www.like.io;

import com.spinner.www.common.io.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class LikeBoardResponse extends BaseResponse {
    private int isLiked;
    private String nickname;
    private Long boardIdx;
}
