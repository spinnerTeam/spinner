package com.spinner.www.file.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.file.entity.Files;
import com.spinner.www.util.ResponseVOUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

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
                    .build();
        File covFile = convertFile(file);
        s3Client.putObject(request, RequestBody.fromFile(covFile));

        Files files = Files.builder()
                .fileOriginName(file.getOriginalFilename())
                .fileConvertName(key)
                .filePath("https://" + bucketName + ".s3.us-east-1.amazonaws.com/" + key)
                .build();

        return files;
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
