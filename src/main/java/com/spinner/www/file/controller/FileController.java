package com.spinner.www.file.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.file.service.S3Service;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private final S3Service s3Service;

    @GetMapping("/{fileIdx}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileIdx") Long id) throws IOException {
        return fileService.downloadFile(id);
    }



}
