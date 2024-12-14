package com.spinner.www.board.io;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BoardCreateRequest {
    private String title;
    private String content;
}
