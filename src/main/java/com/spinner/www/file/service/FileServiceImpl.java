package com.spinner.www.file.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.service.ServerInfo;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.constants.CommonFileCode;
import com.spinner.www.file.dto.FileDto;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.mapper.FileMapper;
import com.spinner.www.file.repository.FileRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepo fileRepo;
    private final FileMapper fileMapper;

    @Autowired
    private ServerInfo serverInfo;

    @Value("${file.upload.path}")
    private String FILE_PATH;


    /**
     * 파일 서버 업로드
     * @param files MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public Map<String, String> uploadBoardFiles(List<MultipartFile> files) {
        // 파일 저장 ID 확인 리스트 세팅
        Map<String, String> fileMap = new HashMap<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    //(1) 파일 서버 저장
                    //(1-1) 폴더가 없으면 폴더 생성
                    String fileUploadPath = fileUploadFolderUpdate(file, FILE_PATH);
                    Long fileTypeCodeIdx = getContentTypeCodeIdx(file);
                    //(1-2) 파일 저장
                    FileDto fileDto = convertFileDto(file, fileUploadPath, fileTypeCodeIdx);
                    String fileUploadPathName = fileUploadPath + "/" + fileDto.getFileConvertName();
                    file.transferTo(new File(fileUploadPathName));
                    Files fileEntity = fileMapper.fileDtoToFile(fileDto);
                    Long fileIdx = saveFile(fileEntity);
                    fileMap.put(fileDto.getFileOriginName(), serverInfo.getServerUrlWithPort() + "/common/file/"+fileIdx);
                } catch (IOException e) {
                    return null;
                }
            }
        }

        return fileMap;
    }

    /**
     * 파일 DB 저장
     * @param fileDBString MultipartFile
     * @return Long
     */
    @Override
    public Long saveFile(Files fileDBString) {
        return fileRepo.save(fileDBString).getFileIdx();
    }

    /**
     * 파일 다운로드
     * @param id Long
     * @return ResponseEntity<Resource>
     */
    @Override
    public ResponseEntity<Resource> downloadFile(Long id) throws IOException {

        Files file = fileRepo.findById(id)
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
        } else if (contentType != null && contentType.startsWith("video")) {
            typePath = "videos";
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
     * 파일 종류에 해당하는 공통코드 리턴
     * @param file MultipartFile
     * @return Long
     */
    public Long getContentTypeCodeIdx(MultipartFile file) throws IOException {
        // 이미지/비디오/문서 파일 분기 작업
        String contentType = file.getContentType();

        if (contentType != null && contentType.startsWith("image")) {
            return CommonFileCode.getCode("image");
        } else if (contentType != null && contentType.startsWith("video")) {
            return CommonFileCode.getCode("video");
        } else {
            return CommonFileCode.getCode("doc");
        }
    }

    /**
     * MultipartFile -> FileDTO 변환
     * @param file MultipartFile
     * @return FileDto
     */
    @Override
    public FileDto convertFileDto(MultipartFile file, String fileUploadPath, Long fileTypeCodeIdx) {
        // 파일 이름이 null일 경우 빈 문자열 처리, 널 포인트 익셉션 방지
        String fileName = Optional.ofNullable(file.getOriginalFilename()).orElse("");

        return FileDto.builder()
                .fileOriginName(fileName)
                .fileConvertName(convertFileName(fileName))
                .filePath(fileUploadPath)
                .fileTypeCodeIdx(fileTypeCodeIdx)
                .build();
    }

    /**
     * 진행중
     * @param file
     * @return
     */
    public Files convertFiles(MultipartFile file){
        String fileName = Optional.ofNullable(file.getOriginalFilename()).orElse("");
        return Files.builder()
                .fileOriginName(fileName)
                .fileConvertName(convertFileName(fileName))
//                .filePath(fileUploadPath)
//                .fileTypeCodeIdx(fileTypeCodeIdx)
                .build();
    }

    @Override
    public Files getFiles(Long idx) {
        return fileRepo.findById(idx).orElseThrow(() -> new NoSuchElementException("파일을 찾을 수 없음"));
    }

    // StudyFile IDX 확인 메서드
    public Files uploadStudyFile(List<MultipartFile> files) {

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    //(1) 파일 서버 저장
                    //(1-1) 폴더가 없으면 폴더 생성
                    String fileUploadPath = fileUploadFolderUpdate(file, FILE_PATH);
                    Long fileTypeCodeIdx = getContentTypeCodeIdx(file);
                    //(1-2) 파일 저장
                    FileDto fileDto = convertFileDto(file, fileUploadPath, fileTypeCodeIdx);
                    String fileUploadPathName = fileUploadPath + "/" + fileDto.getFileConvertName();
                    file.transferTo(new File(fileUploadPathName));
                    //(2) 파일 정보 DB 저장
                    return saveStudyFile(fileMapper.fileDtoToFile(fileDto));
                } catch (IOException e) {
                    log.error("스터디 파일 업로드 중 생긴 익셉션" + e.getMessage());
                }
            }
        }
        return null;
    }

    public Files saveStudyFile(Files fileDBString) {
        return fileRepo.save(fileDBString);
    }

    /**
     * MultipartFile -> File
     * @param file MultipartFile
     * @return File
     * @throws IOException io
     */
    @Override
    public File convertFile(MultipartFile file) throws IOException {
        return File.createTempFile("temp", file.getOriginalFilename());
    }
}