package com.spinner.www.board.io;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BoardUpdateRequest {
    private String title;
    private String content;
}
