package com.spinner.www.common.io;

import lombok.*;

@Getter
public class SearchParamRequest {
    private final Long idx;
    private final int size;
    private final String keyword;

    private final int DEFAULT_SIZE = 5;

    public SearchParamRequest(Long idx, Integer size, String keyword) {
        this.idx = idx;
        this.keyword = keyword;
        this.size = size == null ? DEFAULT_SIZE : size;
    }
}
