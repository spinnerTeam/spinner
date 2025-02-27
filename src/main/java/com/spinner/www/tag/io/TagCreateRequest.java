package com.spinner.www.tag.io;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagCreateRequest {
    private String tagName;
}
