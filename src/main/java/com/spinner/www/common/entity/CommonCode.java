package com.spinner.www.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "common_code")
@Comment("공통코드")
@Getter
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("공통코드 PK")
    private Long codeIdx;

    @Comment("코드명")
    private String codeName;

    @Comment("코드타입")
    private String codeType;

    @Comment("사용유무")
    private boolean codeUse;

    @Comment("표시순서")
    private int codeSort;
}
