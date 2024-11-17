package com.spinner.www.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
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
    private String createdAt;

    @CreatedDate
    @Column(updatable = false , columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String modifiedAt;

    @LastModifiedDate
    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime modifiedDate;

}
