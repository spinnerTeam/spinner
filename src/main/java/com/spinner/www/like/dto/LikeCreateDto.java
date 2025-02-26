package com.spinner.www.like.dto;

import com.spinner.www.common.dto.BaseDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class LikeCreateDto extends BaseDto {
    private Long boardIdx;
    private Long replyIdx;
}
