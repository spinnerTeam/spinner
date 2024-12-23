package com.spinner.www.reply.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReplyUpdateDto {
    private String replyContent;
}
