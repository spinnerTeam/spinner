package com.spinner.www.post.io;

import com.spinner.www.common.io.BaseResponse;
import com.spinner.www.post.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class PostListResponse extends BaseResponse {
    private Long postIdx;
    private String memberNickName;
    private String postTitle;
    private String postContent;

    public PostListResponse(Post entity) {
//        TODO dto 추가 후 처리
//        super(StringUtils.hasText(String.valueOf(entity.getCreatedDate())) ? entity.getCreatedDate().toString() : null, StringUtils.hasText(String.valueOf(entity.getModifiedDate())) ? entity.getModifiedDate().toString() : null);
        this.postIdx = entity.getPostIdx();
        this.memberNickName = entity.getMember().getMemberNickname();
        this.postTitle = entity.getPostTitle();
        this.postContent = entity.getPostContent();
    }
}
