package com.spinner.www.file.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.file.dto.FileDto;
import com.spinner.www.file.entity.Files;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileService {

    ResponseEntity<CommonResponse> uploadFile(MultipartFile file);

    /**
     * 파일 서버 업로드
     * @param files List<MultipartFile>
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> uploadFile(List<MultipartFile> files) throws IOException;

    /**
     * 파일 서버 업로드
     * @param files List<MultipartFile>
     * @return ResponseEntity<CommonResponse>
     */
    Map<String, String> uploadBoardFiles(List<MultipartFile> files) throws IOException;

    /**
     * 파일 DB 저장
     * @param file MultipartFile
     * @return Long
     */
    Long saveFile(Files file);

    /**
     * 파일 다운로드
     * @param id Long
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<Resource> downloadFile(Long id) throws IOException;

    /**
     * 파일 이름 변경
     * @param fileOriginName ConvertFileName
     * @return String
     */
    String convertFileName(String fileOriginName);

    /**
     * 파일 디렉토리 생성 및 업로드 패스 반환
     * @return String
     */
    String fileUploadFolderUpdate(MultipartFile file, String FILE_PATH) throws IOException;

    /**
     * 파일 종류에 해당하는 공통코드 리턴
     * @param file MultipartFile
     * @return Long
     */
    Long getContentTypeCodeIdx(MultipartFile file) throws IOException;

    /**
     * 멀티파트 파일 스트링 -> 파일 DTO 변환
     * @param file MultipartFile
     * @param fileUploadPath String
     * @param contentTypeCodeIdx Long
     * @return FileDto
     */
    FileDto convertFileDto(MultipartFile file, String fileUploadPath, Long contentTypeCodeIdx);

    /**
     * 파일 조회
     * @param idx Long
     * @return Files
     */
    Files getFiles(Long idx);

    /**
     * 파일 스터디
     * @param files List<MultipartFile>
     * @return Files
     */
    Files uploadStudyFile(List<MultipartFile> files);
}
