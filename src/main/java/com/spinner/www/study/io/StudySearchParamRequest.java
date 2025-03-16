package com.spinner.www.study.io;

import com.spinner.www.study.constants.StudySortType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudySearchParamRequest {
    private String keyword;
    private StudySortType sortType;
}
