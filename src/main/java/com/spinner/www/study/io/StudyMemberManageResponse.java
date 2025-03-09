package com.spinner.www.study.io;

import com.spinner.www.study.dto.StudyMemberSelectDto;
import com.spinner.www.study.dto.StudyMemberSelectWaitingDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyMemberManageResponse {
    List<StudyMemberSelectWaitingDto> studyMemberWaitingList;
    List<StudyMemberSelectDto> studyMemberList;

    public StudyMemberManageResponse(List<StudyMemberSelectDto> studyMemberList, List<StudyMemberSelectWaitingDto> studyMemberWaitingList) {
        this.studyMemberList = studyMemberList;
        this.studyMemberWaitingList = studyMemberWaitingList;
    }
}
