package com.spinner.www.tag.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.tag.io.TagCreateRequest;
import com.spinner.www.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;
    /**
     * tag 추가
     */
    @PostMapping
    public ResponseEntity<CommonResponse> insertTag(@RequestBody TagCreateRequest tagCreateRequest) {
        return tagService.insertTag(tagCreateRequest);
    }
}
