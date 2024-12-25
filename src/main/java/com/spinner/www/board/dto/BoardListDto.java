package com.spinner.www.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Data
@Getter
@SuperBuilder
@AllArgsConstructor
public class BoardListDto {
    private Long boardIdx;
    private String boardTitle;
    private String boardContent;
    private String memberNickname;
    private long replyCount;

}
