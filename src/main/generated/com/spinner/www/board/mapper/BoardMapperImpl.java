package com.spinner.www.board.mapper;

import com.spinner.www.board.dto.BoardGetDto;
import com.spinner.www.board.entity.Board;
import com.spinner.www.member.entity.Member;
import com.spinner.www.reply.mapper.ReplyMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T16:56:02+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public BoardGetDto boardToBoardGetDto(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardGetDto.BoardGetDtoBuilder<?, ?> boardGetDto = BoardGetDto.builder();

        boardGetDto.memberNickname( boardMemberMemberNickname( board ) );
        if ( board.getCreatedAt() != null ) {
            boardGetDto.createdAt( String.valueOf( board.getCreatedAt() ) );
        }
        boardGetDto.createdDate( board.getCreatedDate() );
        if ( board.getModifiedAt() != null ) {
            boardGetDto.modifiedAt( String.valueOf( board.getModifiedAt() ) );
        }
        boardGetDto.modifiedDate( board.getModifiedDate() );
        boardGetDto.boardIdx( board.getBoardIdx() );
        boardGetDto.boardTitle( board.getBoardTitle() );
        boardGetDto.boardContent( board.getBoardContent() );
        boardGetDto.replies( replyMapper.replyListToReplyGetDtoList( board.getReplies() ) );

        return boardGetDto.build();
    }

    private String boardMemberMemberNickname(Board board) {
        if ( board == null ) {
            return null;
        }
        Member member = board.getMember();
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
