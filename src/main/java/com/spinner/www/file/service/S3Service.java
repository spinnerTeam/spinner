package com.spinner.www.file.service;

import com.spinner.www.common.entity.CommonCode;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.service.CommonCodeService;
import com.spinner.www.file.entity.Files;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final CommonCodeService commonCodeService;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    /**
     * s3 업로드 파일
     * @param file MultipartFile
     * @return Files
     * @throws IOException Io
     */
    @Transactional
    public Files uploadFile(MultipartFile file, String key) throws IOException {

        // s3 upload
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();
        File covFile = convertFile(file);

        s3Client.putObject(
                request,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        CommonCode commonCode = commonCodeService.covContentType(file.getContentType());

        Files files = Files.builder()
                .fileOriginName(file.getOriginalFilename())
                .fileConvertName(key)
                .filePath("https://" + bucketName + ".s3.us-east-1.amazonaws.com/" + key)
                .commonCode(commonCode)
                .build();

        return files;
    }

    /**
     * 파일 다운로드
     * @param fileKey String
     * @return byte[]
     */
    public byte[] downloadFile(String fileKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();
        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return objectBytes.asByteArray();
    }

    /**
     * 파일 삭제
     * @param fileKey String
     */
    @Transactional
    public ResponseEntity<CommonResponse> deleteFile(String fileKey){
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

    /**
     * MultipartFile -> File
     * @param file MultipartFile
     * @return File
     * @throws IOException io
     */
    public File convertFile(MultipartFile file) throws IOException {
        return File.createTempFile("temp", file.getOriginalFilename());
    }
}
