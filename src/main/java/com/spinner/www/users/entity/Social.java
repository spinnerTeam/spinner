package com.spinner.www.users.entity;

import com.spinner.www.constants.Stype;
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
    private Users users;
    @Comment("소셜 분류")
    private Stype stype;
    @Comment("식별번호")
    private String Snum;
}
