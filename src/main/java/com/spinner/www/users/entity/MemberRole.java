package com.spinner.www.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;


@Entity
@Table(name = "memberRole")
@Comment("유저 권한 테이블")
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("권한PK")
    private int roleIdx;
    @Comment("권한이름")
    private String roleName;
}
