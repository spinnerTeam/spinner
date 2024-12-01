package com.spinner.www.reply.mapper;

import com.spinner.www.reply.dto.ReplyGetDto;
import com.spinner.www.reply.entity.Reply;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReplyMapper {
    @Mapping(target = "memberNickname", source = "member.memberNickname")
    ReplyGetDto replyToReplyGetDto(Reply reply);

    List<ReplyGetDto> replyListToReplyGetDtoList(List<Reply> replies);
}
