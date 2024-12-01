package com.spinner.www.post.dto;

import com.spinner.www.common.dto.BaseDto;
import com.spinner.www.reply.dto.ReplyGetDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class PostGetDto extends BaseDto {
    private Long postIdx;
    private String postTitle;
    private String postContent;
    private String memberNickname;
    private List<ReplyGetDto> replies;
}
