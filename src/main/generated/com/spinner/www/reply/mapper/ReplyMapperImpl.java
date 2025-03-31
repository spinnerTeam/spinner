package com.spinner.www.reply.mapper;

import com.spinner.www.member.entity.Member;
import com.spinner.www.reply.dto.ReplyCreateDto;
import com.spinner.www.reply.dto.ReplyGetDto;
import com.spinner.www.reply.dto.ReplyUpdateDto;
import com.spinner.www.reply.entity.Reply;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyResponse;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:02+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ReplyMapperImpl implements ReplyMapper {

    @Override
    public ReplyGetDto replyToReplyGetDto(Reply reply) {
        if ( reply == null ) {
            return null;
        }

        ReplyGetDto.ReplyGetDtoBuilder<?, ?> replyGetDto = ReplyGetDto.builder();

        replyGetDto.memberNickname( replyMemberMemberNickname( reply ) );
        if ( reply.getCreatedAt() != null ) {
            replyGetDto.createdAt( String.valueOf( reply.getCreatedAt() ) );
        }
        replyGetDto.createdDate( reply.getCreatedDate() );
        if ( reply.getModifiedAt() != null ) {
            replyGetDto.modifiedAt( String.valueOf( reply.getModifiedAt() ) );
        }
        replyGetDto.modifiedDate( reply.getModifiedDate() );
        replyGetDto.boardIdx( reply.getBoardIdx() );
        replyGetDto.replyIdx( reply.getReplyIdx() );
        replyGetDto.replyContent( reply.getReplyContent() );
        replyGetDto.childReplies( replyListToReplyGetDtoList( reply.getChildReplies() ) );

        return replyGetDto.build();
    }

    @Override
    public ReplyResponse replyToReplyResponse(Reply reply) {
        if ( reply == null ) {
            return null;
        }

        ReplyResponse.ReplyResponseBuilder<?, ?> replyResponse = ReplyResponse.builder();

        replyResponse.nickname( replyMemberMemberNickname( reply ) );
        replyResponse.content( reply.getReplyContent() );
        if ( reply.getCreatedAt() != null ) {
            replyResponse.createdAt( String.valueOf( reply.getCreatedAt() ) );
        }
        replyResponse.createdDate( reply.getCreatedDate() );
        if ( reply.getModifiedAt() != null ) {
            replyResponse.modifiedAt( String.valueOf( reply.getModifiedAt() ) );
        }
        replyResponse.modifiedDate( reply.getModifiedDate() );
        replyResponse.childReplies( replyListToReplyResponseList( reply.getChildReplies() ) );

        return replyResponse.build();
    }

    @Override
    public ReplyCreateDto replyCreateRequestToReplyCreateDto(ReplyCreateRequest replyRequest) {
        if ( replyRequest == null ) {
            return null;
        }

        ReplyCreateDto.ReplyCreateDtoBuilder<?, ?> replyCreateDto = ReplyCreateDto.builder();

        replyCreateDto.replyContent( replyRequest.getContent() );
        replyCreateDto.boardIdx( replyRequest.getBoardIdx() );
        replyCreateDto.parentIdx( replyRequest.getParentIdx() );

        return replyCreateDto.build();
    }

    @Override
    public ReplyUpdateDto replyUpdateRequestToReplyUpdateDto(ReplyUpdateRequest replyRequest) {
        if ( replyRequest == null ) {
            return null;
        }

        ReplyUpdateDto.ReplyUpdateDtoBuilder<?, ?> replyUpdateDto = ReplyUpdateDto.builder();

        replyUpdateDto.replyContent( replyRequest.getContent() );

        return replyUpdateDto.build();
    }

    @Override
    public List<ReplyGetDto> replyListToReplyGetDtoList(List<Reply> replies) {
        if ( replies == null ) {
            return null;
        }

        List<ReplyGetDto> list = new ArrayList<ReplyGetDto>( replies.size() );
        for ( Reply reply : replies ) {
            list.add( replyToReplyGetDto( reply ) );
        }

        return list;
    }

    private String replyMemberMemberNickname(Reply reply) {
        if ( reply == null ) {
            return null;
        }
        Member member = reply.getMember();
        if ( member == null ) {
            return null;
        }
        String memberNickname = member.getMemberNickname();
        if ( memberNickname == null ) {
            return null;
        }
        return memberNickname;
    }

    protected List<ReplyResponse> replyListToReplyResponseList(List<Reply> list) {
        if ( list == null ) {
            return null;
        }

        List<ReplyResponse> list1 = new ArrayList<ReplyResponse>( list.size() );
        for ( Reply reply : list ) {
            list1.add( replyToReplyResponse( reply ) );
        }

        return list1;
    }
}
