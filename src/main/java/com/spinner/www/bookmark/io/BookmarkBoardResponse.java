package com.spinner.www.bookmark.io;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spinner.www.common.io.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class BookmarkBoardResponse extends BaseResponse {
    @JsonProperty("isBookmarked")
    private boolean bookmarked;
    private String nickname;
    private Long boardIdx;
    private String boardName;
}
