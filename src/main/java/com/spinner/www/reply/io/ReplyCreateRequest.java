package com.spinner.www.reply.io;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReplyCreateRequest {
    private Long boardIdx;
    private Long parentIdx;
    private String content;
}
