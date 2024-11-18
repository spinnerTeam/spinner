package com.spinner.www.post.io;

import com.spinner.www.common.io.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class PostResponse extends BaseResponse {
    private Long postIdx;
    private Long memberIdx;
    private String memberNickName;
    private String postTitle;
    private String postContent;
}
