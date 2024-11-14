package com.spinner.www.file.controller;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 파일 유틸 컨트롤러
 * 포스트맨 테스트 시 사용
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/common/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadFile(@RequestParam("multiFile") List<MultipartFile> files) throws IOException {
        return fileService.uploadFile(files);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) throws IOException {
        return fileService.downloadFile(id);
    }
}
