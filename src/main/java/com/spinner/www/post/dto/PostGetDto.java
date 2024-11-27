package com.spinner.www.post.dto;

import com.spinner.www.common.dto.BaseDto;
import com.spinner.www.member.entity.Member;

public class PostGetDto extends BaseDto {
    private Long postIdx;
    private String postTitle;
    private String postContent;
    private Member member;
}
