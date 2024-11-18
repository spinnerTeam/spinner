package com.spinner.www.post.io;

import com.spinner.www.post.entity.Post;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostCreateRequest {
    private Long postIdx;
    private String postTitle;
    private String postContent;

    public void PostCreateRequestIO(Post entity) {
        this.postIdx = getPostIdx();
        this.postTitle = entity.getPostTitle();
        this.postContent = entity.getPostContent();
    }
}
