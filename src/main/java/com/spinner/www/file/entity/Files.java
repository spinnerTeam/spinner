package com.spinner.www.file.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "File")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Comment("파일 테이블")
public class Files {

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

    @CreatedBy
    @Column(updatable = false)
    private String createdAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdDate;

    @Column(updatable = false, columnDefinition = "TIME(0)")
    private LocalTime createdTime;
}
