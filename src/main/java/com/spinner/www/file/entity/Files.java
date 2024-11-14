package com.spinner.www.file.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "File")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("파일 테이블")
public class Files {

    /**
     * [MEMO:hyper]
     * 멤버 추가 후 파일 업로더 작업
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileIdx")
    @Comment("파일 PK")
    private Long id;

    @Comment("파일 원본명")
    private String fileOriginName;

    @Comment("파일 변경명")
    private String fileConvertName;

    @Comment("파일 경로")
    private String filePath;

    @Comment("파일 생성 날짜")
    private LocalDateTime createdDatetime;

    @Comment("파일 업로더")
    private LocalTime createAt;
}
