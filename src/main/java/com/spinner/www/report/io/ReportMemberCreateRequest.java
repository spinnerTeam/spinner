package com.spinner.www.report.io;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportMemberCreateRequest {
    @NotBlank(message = "신고 타입은 필수 입력 조건입니다.")
    private Long reportTypeIdx;
    @NotBlank(message = "게시물 넘버는 필수 입력 조건입니다.")
    private Long memberIdx;
}
