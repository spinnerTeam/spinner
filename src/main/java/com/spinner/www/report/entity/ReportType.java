package com.spinner.www.report.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.report.dto.ReportTypeDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportTypeIdx")
    @Comment("신고_타입 PK")
    private Long id;

    @Comment("신고_타입 내용")
    private String reportTypeContent;

    @Comment("신고_타입 삭제 여부")
    private String reportTypeIsRemoved;

    /**
     * 생성 메서드
     */
    public static ReportType create(ReportTypeDto reportTypeDto) {
        return ReportType.builder()
                .reportTypeContent(reportTypeDto.getReportTypeContent())
                .reportTypeIsRemoved("N")
                .build();
    }
}
