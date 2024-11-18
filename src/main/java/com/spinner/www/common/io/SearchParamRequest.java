package com.spinner.www.common.io;

import lombok.*;

@Getter
public class SearchParamRequest {
    private Long id;
    private int size;
    private String keyword;

    private final int DEFAULT_SIZE = 5;

    public SearchParamRequest(Long id, Integer size, String keyword) {
        this.id = id;
        this.keyword = keyword;
        this.size = size == null ? DEFAULT_SIZE : size;
    }
}
