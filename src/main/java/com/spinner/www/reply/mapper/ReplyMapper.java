package com.spinner.www.reply.mapper;

import com.spinner.www.reply.dto.ReplyCreateDto;
import com.spinner.www.reply.dto.ReplyGetDto;
import com.spinner.www.reply.dto.ReplyUpdateDto;
import com.spinner.www.reply.entity.Reply;
import com.spinner.www.reply.io.ReplyCreateRequest;
import com.spinner.www.reply.io.ReplyResponse;
import com.spinner.www.reply.io.ReplyUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReplyMapper {
    @Mapping(target = "memberNickname", source = "member.memberNickname")
    ReplyGetDto replyToReplyGetDto(Reply reply);

    @Mapping(target = "nickname", source = "member.memberNickname")
    @Mapping(target = "content", source = "replyContent")
    ReplyResponse replyToReplyResponse(Reply reply);

    @Mapping(target = "replyContent", source = "content")
    ReplyCreateDto replyCreateRequestToReplyCreateDto(ReplyCreateRequest replyRequest);

    @Mapping(target = "replyContent", source = "content")
    ReplyUpdateDto replyUpdateRequestToReplyUpdateDto(ReplyUpdateRequest replyRequest);

    List<ReplyGetDto> replyListToReplyGetDtoList(List<Reply> replies);
}
