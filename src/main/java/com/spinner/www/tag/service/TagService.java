package com.spinner.www.tag.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.tag.io.TagCreateRequest;
import org.springframework.http.ResponseEntity;

public interface TagService {
    ResponseEntity<CommonResponse> insertTag(TagCreateRequest tagCreateRequest);
}
