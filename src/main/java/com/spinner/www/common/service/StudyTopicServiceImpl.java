package com.spinner.www.common.service;

import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.common.repository.MenuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudyTopicServiceImpl implements StudyTopicService {

    private final MenuRepo menuRepo;

    /**
     * menuIdx로 Menu 조회
     * @param menuIdx Long
     * @return Menu
     */
    @Override
    public Optional<StudyTopic> getStudyTopicByStudyTopicIdx(Long menuIdx) {
        return menuRepo.findByStudyTopicIdx(menuIdx);
    }

    /**
     * 전체 메뉴 조회
     * @return List<Menu>
     */
    @Override
    public List<StudyTopic> getAll() {
        return menuRepo.findAll();
    }

    /**
     * 전체 관심분야 조회(부모 메뉴가 있는 경우)
     * @return List<Menu>
     */
    @Override
    public List<StudyTopic> getAllInterest() {
        return menuRepo.findAllByStudyTopicParentIdxIsNotNull();
    }
}
