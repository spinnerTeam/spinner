package com.spinner.www.common.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "menu")
@Comment("메뉴")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("메뉴 PK")
    private Long menuIdx;

    @Comment("메뉴 뎁스")
    private Integer menuDepth;

    @Comment("부모메뉴 IDX")
    private Long menuParentIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_idx")
    private CommonCode commonCode;
}
