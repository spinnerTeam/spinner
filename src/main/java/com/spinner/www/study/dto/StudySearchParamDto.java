package com.spinner.www.study.dto;

import com.spinner.www.study.constants.StudySortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudySearchParamDto {
    private String keyword;
    private StudySortType sortType;
}
