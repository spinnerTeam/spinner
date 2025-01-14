package com.spinner.www.like.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LikeCreateRequest {
    private Long boardIdx;
    private Long replyIdx;
}
