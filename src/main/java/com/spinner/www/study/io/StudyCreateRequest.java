package com.spinner.www.study.io;

import com.spinner.www.study.constants.StudyCategoryType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StudyCreateRequest {

    @NotBlank
    @Size(min = 3, max = 10, message = "스터디 이름은 최소 3자 이상, 10자 미만입니다.")
    private String studyName;

    @NotBlank
    @Size(min = 10, max = 100, message = "최소 10자 이상 작성해 주세요. 최대 100자입니다.")
    private String studyIntro;
    private StudyCategoryType studyCategoryType;

    @Min(value = 2, message = "최소 2명 이상 모임 생성이 가능합니다.")
    @Max(value = 15, message = "최대 15명만 가입할 수 있습니다.")
    private int studyMaxPeople;
}
