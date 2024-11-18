package com.spinner.www.post.io;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostUpdateRequest {
    private String postTitle;
    private String postContent;
}
