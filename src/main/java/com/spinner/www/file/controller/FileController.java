package com.spinner.www.file.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.file.service.FileService;
import com.spinner.www.file.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    /**
     * 이미지 업로드 (테스트용도)
     * @param file MultipartFile
     * @return ResponseEntity<CommonResponse>
     * @throws IOException throws
     */
    @Operation(
            summary = "파일업로드 테스트 API",
            description = "파일 업로드 테스트용입니다."
    )
    @Parameters({
            @Parameter(name = "file", description = "파일"),
    })
    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadFile(@RequestPart(name = "file") MultipartFile file) throws IOException {
        return fileService.uploadFiles(file);
    }
}
