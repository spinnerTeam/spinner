package com.spinner.www.reply.dto;

import com.spinner.www.common.dto.BaseDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ReplyCreateDto extends BaseDto {
    private Long boardIdx;
    private Long parentIdx;
    private String replyContent;
}
