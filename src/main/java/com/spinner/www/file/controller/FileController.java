package com.spinner.www.file.controller;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.service.CommonCodeService;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.file.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    private final CommonCodeService commonCodeService;

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
    //    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadFile(@RequestPart(name = "file") MultipartFile file) throws IOException {
        return fileService.uploadFiles(file);
    }

    /**
     * 파일 다운로드
     * @param idx Long
     * @return ResponseEntity<byte[]>
     */
    @Operation(
            summary = "파일다운로드 API",
            description = "파일/이미지/동영상 다운로드입니다."
    )
    @Parameters({
            @Parameter(name = "fileIdx", description = "파일번호"),
    })
    //    @PreAuthorize("isAuthenticated()")
    @GetMapping("/downFile/{fileIdx}")
    public ResponseEntity<byte[]> downFile(@PathVariable("fileIdx") Long idx){
        Files files = fileService.getFiles(idx);
        byte[] fileBytes = s3Service.downloadFile(files.getFileConvertName());
        return ResponseEntity.ok()
                .contentType(fileService.covMediaType(files.getCommonCode().getCodeName()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + files.getFileOriginName() + "\"")
                .body(fileBytes);
    }

    /**
     * s3 파일 삭제
     * @param idx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Operation(
            summary = "파일 삭제 API",
            description = "파일/이미지/동영상 삭제입니다."
    )
    @Parameters({
            @Parameter(name = "fileIdx", description = "파일번호"),
    })
    //    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/deleteFile/{fileIdx}")
    public ResponseEntity<CommonResponse> deleteFile(@PathVariable("fileIdx") Long idx){
        return fileService.deleteFile(idx);
    }
}
