package com.spinner.www.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BaseEntity 공통 컬럼
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    @Comment("생성자")
    private Long createdAt;

    @CreatedDate
    @Column(updatable = false, columnDefinition = "TIMESTAMP(0)")
    @Comment("생성일")
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Comment("수정자")
    private Long modifiedAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP(0)")
    @Comment("수정일")
    private LocalDateTime modifiedDate;

}
