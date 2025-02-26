package com.spinner.www.study.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.study.dto.StudyCreateDto;
import com.spinner.www.study.io.StudyCreateRequest;
import com.spinner.www.study.io.StudyUpdateRequest;
import com.spinner.www.study.mapper.StudyMapper;
import com.spinner.www.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyServiceImpl implements StudyService {
    private final StudyRepository studyRepository;
    private final StudyMapper studyMapper;

    @Override
    public ResponseEntity<CommonResponse> getStudyList() {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> getStudy(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> createStudy(StudyCreateRequest studyCreateRequest) {

        // 스터디 개설
        StudyCreateDto studyCreateDto = studyMapper.toStudyCreateDto(studyCreateRequest);

        // 스터디 멤버에 로그인된 유저 넣기
        // 로그인된 유저가 스터디 장
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> updateStudy(Long id,
        StudyUpdateRequest studyUpdateRequest) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse> deleteStudy(Long id) {
        return null;
    }
}
