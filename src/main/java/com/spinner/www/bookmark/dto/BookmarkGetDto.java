package com.spinner.www.bookmark.dto;

import com.spinner.www.common.dto.BaseDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BookmarkGetDto extends BaseDto {
    private Long boardIdx;
    private Long replyIdx;
    private String replyContent;
    private String memberNickname;
    private List<BookmarkGetDto> childReplies;
}
