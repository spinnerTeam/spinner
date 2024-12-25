package com.spinner.www.board.mapper;

import com.spinner.www.board.dto.BoardGetDto;
import com.spinner.www.board.entity.Board;
import com.spinner.www.reply.mapper.ReplyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ReplyMapper.class})
public interface BoardMapper {
//    Board boardDtoToBoard(BoardGetDto boardGetDto);
    @Mapping(target = "memberNickname", source = "member.memberNickname")
    BoardGetDto boardToBoardGetDto(Board board);
//    List<BoardGetDto> boardListToBoardGetDtoList(List<Board> boards);
}
