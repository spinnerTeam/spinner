package com.spinner.www.member.entity;

import com.spinner.www.member.constants.SocialType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Comment;


@Entity
@Table(name = "social")
@Comment("소셜 테이블")
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("소셜PK")
    private Long sIdx;
    @ManyToOne
    @JoinColumn(name = "uIdx")
    @Comment("유저 테이블")
    private Member member;
    @Comment("소셜 분류")
    private SocialType socialtype;
    @Comment("식별번호")
    private String socialNum;
}
