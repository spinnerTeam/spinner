package com.spinner.www.board.dto;

import com.spinner.www.common.dto.BaseDto;
import com.spinner.www.reply.dto.ReplyGetDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BoardGetDto extends BaseDto {
    private Long boardIdx;
    private String boardTitle;
    private String boardContent;
    private String memberNickname;
    private List<ReplyGetDto> replies;
}
