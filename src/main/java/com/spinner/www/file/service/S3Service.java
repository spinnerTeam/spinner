package com.spinner.www.file.service;

import com.spinner.www.common.io.CommonResponse;
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
    private final FileService fileService;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    /**
     * s3 업로드 파일
     * @param files List<MultipartFile>
     * @throws IOException Io
     * https://spinnerbucket.s3.us-east-1.amazonaws.com/1d5f8672-349b-4021-b5a2-9bd62cf8de00.png
     */
    @Transactional
    public String uploadFile(List<MultipartFile> files) throws IOException {

        // s3 upload
        for(MultipartFile file : files){
            String key = fileService.convertFileName(file.getOriginalFilename());
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            File covFile = fileService.convertFile(file);
            s3Client.putObject(request, RequestBody.fromFile(covFile));
        }
    }
}
