package com.spinner.www.file.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.file.constants.CommonFileCode;
import com.spinner.www.file.dto.FileDto;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.mapper.FileMapper;
import com.spinner.www.file.repository.FileRepo;
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
    private final S3Service s3Service;


    @Value("${file.upload.path}")
    private String FILE_PATH;

    /**
     * 파일 한개만 받을 때 List<MultipartFile> 로 변환
     * @param files MultipartFile
     * @return List<MultipartFile>
     * @throws IOException Io
     */
    @Override
    public ResponseEntity<CommonResponse> uploadFiles(MultipartFile files) throws IOException {
        return uploadFiles(Collections.singletonList(files));
    }

    /**
     * 멀티파트 파일을 업로드하면 파일 객체로 돌려줌 기능이 필요해서 분리함
     * @param file MultipartFile
     * @return Files
     * @throws IOException  IOException
     */
    @Override
    public Files uploadAndSaveFile(MultipartFile file) throws IOException {
        String key = convertFileName(Objects.requireNonNull(file.getOriginalFilename()));
        Files filesEntity = s3Service.uploadFile(file, key);
        return saveFile(filesEntity);
    }

    /**
     * 파일 s3에 업로드 후 디비 저장
     * @param files List<MultipartFile>
     * @return ResponseEntity
     * @throws IOException Io
     */
    @Override
    public ResponseEntity<CommonResponse> uploadFiles(List<MultipartFile> files) throws IOException {
        List<Long> fileUploadResults = new ArrayList<>();
        for (MultipartFile file : files){
            Files filesEntity = uploadAndSaveFile(file);
            fileUploadResults.add(filesEntity.getFileIdx());
        }
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(fileUploadResults), HttpStatus.OK);
    }

    /**
     * 파일 서버 업로드
     * @param uploadFiles List<MultipartFile>
     * @return List<Files>
     */
    @Override
    public List<Files> uploadBoardFiles(List<MultipartFile> uploadFiles) throws IOException {
        if(Objects.isNull(uploadFiles) || uploadFiles.isEmpty()) return null;
        List<Files> files = new ArrayList<>();
        for (MultipartFile uploadFile : uploadFiles) {
            Files fileEntity = uploadAndSaveFile(uploadFile);
            files.add(fileEntity);
        }
        return files;
    }



    /**
     * 파일 DB 저장
     * @param fileDBString MultipartFile
     * @return Long
     */
    @Override
    public Files saveFile(Files fileDBString) {
        return fileRepo.save(fileDBString);
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
//                    return saveStudyFile(fileMapper.fileDtoToFile(fileDto));
                    return null;
                } catch (IOException e) {
                    log.error("스터디 파일 업로드 중 생긴 익셉션" + e.getMessage());
                }
            }
        }
        return null;
    }

    /**
     * 파일 저장
     * @param fileDBString Files
     */
    public Files saveStudyFile(Files fileDBString) {
        return fileRepo.save(fileDBString);
    }

    /**
     * Common의 타입을 midiaType으로 변경
     * @param commonName String
     * @return MediaType
     */
    @Override
    public MediaType covMediaType(String commonName) {

        MediaType mediaType;
        switch (commonName) {
            case "IMAGE":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "VIDEO":
                mediaType = MediaType.valueOf("video/mp4");
                break;
            case "DOCUMENT":
                mediaType = MediaType.APPLICATION_PDF;
                break;
            case "FILE":
            default:
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
                break;
        }
        return mediaType;
    }

    /**
     * 파일 삭제
     * @param fileIdx Long
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> deleteFile(Long fileIdx) {
        Files files = getFiles(fileIdx);
        fileRepo.deleteById(fileIdx);
        s3Service.deleteFile(files.getFileConvertName());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }
}