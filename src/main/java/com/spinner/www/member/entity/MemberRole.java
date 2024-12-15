package com.spinner.www.member.entity;

import com.spinner.www.member.constants.RoleName;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
