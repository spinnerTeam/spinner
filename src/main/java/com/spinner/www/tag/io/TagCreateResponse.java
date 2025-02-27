package com.spinner.www.tag.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagCreateResponse {

    private Long tagIdx;
    private String tagName;
}
