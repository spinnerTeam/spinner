package com.spinner.www.file.controller;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 파일 유틸 컨트롤러
 * 포스트맨 테스트 시 사용
 */
@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/common/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadFile(@RequestParam("multiFile") List<MultipartFile> files) {
        return fileService.uploadFile(files);
    }
}
