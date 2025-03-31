package com.spinner.www.like.mapper;

import com.spinner.www.like.dto.LikeGetDto;
import com.spinner.www.like.entity.Like;
import com.spinner.www.like.io.LikeBoardResponse;
import com.spinner.www.like.io.LikeReplyResponse;
import com.spinner.www.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:02+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class LikeMapperImpl implements LikeMapper {

    @Override
    public LikeGetDto likeToLikeGetDto(Like like) {
        if ( like == null ) {
            return null;
        }

        LikeGetDto.LikeGetDtoBuilder<?, ?> likeGetDto = LikeGetDto.builder();

        likeGetDto.memberNickname( likeMemberMemberNickname( like ) );
        if ( like.getCreatedAt() != null ) {
            likeGetDto.createdAt( String.valueOf( like.getCreatedAt() ) );
        }
        likeGetDto.createdDate( like.getCreatedDate() );
        if ( like.getModifiedAt() != null ) {
            likeGetDto.modifiedAt( String.valueOf( like.getModifiedAt() ) );
        }
        likeGetDto.modifiedDate( like.getModifiedDate() );
        likeGetDto.boardIdx( like.getBoardIdx() );
        likeGetDto.replyIdx( like.getReplyIdx() );

        return likeGetDto.build();
    }

    @Override
    public LikeBoardResponse likeToLikeBoardResponse(Like like) {
        if ( like == null ) {
            return null;
        }

        LikeBoardResponse.LikeBoardResponseBuilder<?, ?> likeBoardResponse = LikeBoardResponse.builder();

        likeBoardResponse.nickname( likeMemberMemberNickname( like ) );
        likeBoardResponse.isLiked( like.getLikeIsLiked() );
        if ( like.getCreatedAt() != null ) {
            likeBoardResponse.createdAt( String.valueOf( like.getCreatedAt() ) );
        }
        likeBoardResponse.createdDate( like.getCreatedDate() );
        if ( like.getModifiedAt() != null ) {
            likeBoardResponse.modifiedAt( String.valueOf( like.getModifiedAt() ) );
        }
        likeBoardResponse.modifiedDate( like.getModifiedDate() );
        likeBoardResponse.boardIdx( like.getBoardIdx() );

        return likeBoardResponse.build();
    }

    @Override
    public LikeReplyResponse likeToLikeReplyResponse(Like like) {
        if ( like == null ) {
            return null;
        }

        LikeReplyResponse.LikeReplyResponseBuilder<?, ?> likeReplyResponse = LikeReplyResponse.builder();

        likeReplyResponse.nickname( likeMemberMemberNickname( like ) );
        likeReplyResponse.isLiked( like.getLikeIsLiked() );
        if ( like.getCreatedAt() != null ) {
            likeReplyResponse.createdAt( String.valueOf( like.getCreatedAt() ) );
        }
        likeReplyResponse.createdDate( like.getCreatedDate() );
        if ( like.getModifiedAt() != null ) {
            likeReplyResponse.modifiedAt( String.valueOf( like.getModifiedAt() ) );
        }
        likeReplyResponse.modifiedDate( like.getModifiedDate() );
        likeReplyResponse.replyIdx( like.getReplyIdx() );

        return likeReplyResponse.build();
    }

    private String likeMemberMemberNickname(Like like) {
        if ( like == null ) {
            return null;
        }
        Member member = like.getMember();
        if ( member == null ) {
            return null;
        }
        String memberNickname = member.getMemberNickname();
        if ( memberNickname == null ) {
            return null;
        }
        return memberNickname;
    }
}
