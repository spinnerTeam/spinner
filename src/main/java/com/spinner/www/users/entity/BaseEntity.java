package com.spinner.www.users.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;

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
    @Column(updatable = false)
    private LocalDate createdDate;

    @Column(updatable = false , columnDefinition = "TIME(0)")
    private LocalTime createdTime;

    @LastModifiedBy
    private String modifiedAt;

    @LastModifiedDate
    private LocalDate modifiedDate;

    @LastModifiedDate
    @Column(columnDefinition = "TIME(0)")
    private LocalTime modifiedTime;


}
