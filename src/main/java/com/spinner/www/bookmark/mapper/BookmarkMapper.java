package com.spinner.www.bookmark.mapper;

import com.spinner.www.bookmark.entity.Bookmark;
import com.spinner.www.bookmark.io.BookmarkBoardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookmarkMapper {

    @Mapping(target = "nickname", source = "member.memberNickname")
    @Mapping(target = "boardIdx", source = "board.boardIdx")
    @Mapping(target = "boardName", source = "board.commonCode.codeName")
    @Mapping(target = "bookmarked", expression = "java(bookmark.getIsBookmarked() == 1)")
    BookmarkBoardResponse bookmarkToBookmarkBoardResponse(Bookmark bookmark);
}
