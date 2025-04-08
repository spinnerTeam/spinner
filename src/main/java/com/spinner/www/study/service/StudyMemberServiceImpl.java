package com.spinner.www.study.service;

import com.spinner.www.member.entity.Member;
import com.spinner.www.study.constants.StudyMemberStatus;
import com.spinner.www.study.entity.Study;
import com.spinner.www.study.repository.StudyMemberRepo;
import com.spinner.www.study.entity.StudyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyMemberServiceImpl implements StudyMemberService{

    private final StudyMemberRepo studyMemberRepo;

    /**
     * 스터디 멤버 저장
     * @param studyMember  StudyMember
     */
    @Override
    public void saveStudyMember(StudyMember studyMember) {
        studyMemberRepo.save(studyMember);
    }

    /**
     * memberIdx 별 스터디 멤버 조회
     * @param member Member
     * @param studyIdx Long
     * @return StudyMember
     */
    @Override
    public StudyMember getStudyMember(Member member ,Long studyIdx) {
        return studyMemberRepo.findByMemberAndStudyMemberIdx(member ,studyIdx);
    }

    /**
     * 스터디 가입 여부
     * @param studyMemberIdx Long
     * @param member Member
     * @return boolean
     */
    @Override
    public boolean existsByStudyMemberIdxAndMember(Long studyMemberIdx, Member member) {
        return studyMemberRepo.existsByStudyMemberIdxAndMember(studyMemberIdx, member);
    }

    /**
     *  studyMember 객체 조회
     * @param member Member
     * @param study Study
     * @return StudyMember
     */
    @Override
    public StudyMember getStudyMember(Member member, Study study){
        return studyMemberRepo.findByMemberAndStudy(member, study);
    }

    /**
     * 스터디 가입 여부
     * @param studyIdx Long
     * @param member Member
     * @return boolean
     */
    @Override
    public boolean isStudyMember(Long studyIdx ,Member member){
        return existsByStudyMemberIdxAndMember(studyIdx, member);
    }

    /**
     * 스터디 멤버 상태별 조회
     * @param member Member
     * @param study Study
     * @param studyMemberStatus StudyMemberStatus
     * @return Optional<StudyMember>
     */
    @Override
    public Optional<StudyMember> findByMemberAndStudyAndStudyMemberStatus(Member member, Study study, StudyMemberStatus studyMemberStatus) {
        return studyMemberRepo.findByMemberAndStudyAndStudyMemberStatus(member, study, studyMemberStatus);
    }

    /**
     * 스터디 멤버 삭제
     * @param studyMemberIdx Long
     */
    @Override
    public void deleteById(Long studyMemberIdx) {
        studyMemberRepo.deleteById(studyMemberIdx);
    }
}
