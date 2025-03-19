package com.spinner.www.like.mapper;

import com.spinner.www.like.dto.LikeCreateDto;
import com.spinner.www.like.dto.LikeGetDto;
import com.spinner.www.like.dto.LikeUpdateDto;
import com.spinner.www.like.entity.Like;
import com.spinner.www.like.io.LikeCreateRequest;
import com.spinner.www.like.io.LikeBoardResponse;
import com.spinner.www.like.io.LikeReplyResponse;
import com.spinner.www.like.io.LikeUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(target = "memberNickname", source = "member.memberNickname")
    LikeGetDto likeToLikeGetDto(Like like);

    @Mapping(target = "nickname", source = "member.memberNickname")
    @Mapping(target = "liked", expression = "java(like.getLikeIsLiked() == 1)")
    LikeBoardResponse likeToLikeBoardResponse(Like like);

    @Mapping(target = "nickname", source = "member.memberNickname")
    @Mapping(target = "liked", expression = "java(like.getLikeIsLiked() == 1)")
    LikeReplyResponse likeToLikeReplyResponse(Like like);

}
