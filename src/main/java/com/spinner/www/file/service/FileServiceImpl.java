package com.spinner.www.file.service;

import com.spinner.www.common.CommonResponse;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.dto.FileDto;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.mapper.FileMapper;
import com.spinner.www.file.repository.FileRepository;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Value("${file.upload.path}")
    private String FILE_PATH;

    /**
     * 파일 서버 업로드
     * @param files MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> uploadFile(List<MultipartFile> files) {
        // 파일 저장 ID 확인 리스트 세팅
        List<Long> fileUploadResults = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    //(1) 파일 서버 저장
                    //(1-1) 폴더가 없으면 폴더 생성
                    String fileUploadPath = fileUploadFolderUpdate(file, FILE_PATH);
                    //(1-2) 파일 저장
                    FileDto fileDto = convertFileDto(file, fileUploadPath);
                    String fileUploadPathName = fileUploadPath + "/" + fileDto.getFileConvertName();
                    file.transferTo(new File(fileUploadPathName));

                    //(2) 파일 정보 DB 저장
                    fileUploadResults.add(saveFile(fileMapper.fileDtoToFile(fileDto)));
                } catch (IOException e) {
                    return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FILE_UPLOAD_FAIL), HttpStatus.CONFLICT);
                }
            }
        }

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(fileUploadResults), HttpStatus.OK);
    }

    /**
     * 파일 DB 저장
     * @param fileDBString MultipartFile
     * @return Long
     */
    @Override
    public Long saveFile(Files fileDBString) {
        return fileRepository.save(fileDBString).getId();
    }

    /**
     * 파일 다운로드
     * @param id Long
     * @return ResponseEntity<Resource>
     */
    @Override
    public ResponseEntity<Resource> downloadFile(Long id) throws IOException {

        Files file = fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("File not found with id: " + id));
        Path path = Paths.get(file.getFilePath() + "/" + file.getFileConvertName());
        Resource resource = new InputStreamResource(java.nio.file.Files.newInputStream(path));

        // 파일 이름 인코딩 처리 (UTF-8)
        String encodedFileName = URLEncoder.encode(file.getFileOriginName(), StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;  filename=\"" + encodedFileName + "\"")
                .body(resource);
    }

    /**
     * UUID 파일명 변경
     * @param fileOriginName ConvertFileName
     * @return String
     */
    @Override
    public String convertFileName(String fileOriginName) {
        String extension = fileOriginName.substring(fileOriginName.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        return uuid + extension;
    }

    /**
     * 파일 업로드 폴더 생성 및 패스 반환
     * @param FILE_PATH String
     * @return String
     */
    @Override
    public String fileUploadFolderUpdate(MultipartFile file, String FILE_PATH) throws IOException {

        // 이미지/파일 분기 작업
        String contentType = file.getContentType();
        String typePath;

        if (contentType != null && contentType.startsWith("image")) {
            typePath = "images";
        } else {
            typePath = "files";
        }

        // 날짜 분기 작업
        LocalDate now = LocalDate.now();
        String yearFolder = String.valueOf(now.getYear());
        String monthFolder = String.valueOf(now.getMonthValue());

        // 경로 설정
        String directoryPath = FILE_PATH + "/" + typePath + "/" + yearFolder + "/" + monthFolder;

        // 폴더가 존재하지 않으면 생성
        java.nio.file.Files.createDirectories(Paths.get(directoryPath));

        return directoryPath;
    }

    /**
     * MultipartFile -> FileDTO 변환
     * @param file MultipartFile
     * @return FileDto
     */
    @Override
    public FileDto convertFileDto(MultipartFile file, String fileUploadPath) {
        // 파일 이름이 null일 경우 빈 문자열 처리, 널 포인트 익셉션 방지
        String fileName = Optional.ofNullable(file.getOriginalFilename()).orElse("");

        return FileDto.builder()
                .fileOriginName(fileName)
                .fileConvertName(convertFileName(fileName))
                .filePath(fileUploadPath)
                .createdDatetime(LocalDateTime.now())
                .build();
    }
}