package com.spinner.www.post.io;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String content;
}
