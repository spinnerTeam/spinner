package com.spinner.www.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("유저 테이블")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("유저PK")
    private Long uIdx;

    @ManyToOne
    @JoinColumn(name = "roIdx")
    @Comment("유저 권한")
    private UserRole userRole;
    @Comment("이메일")
    private String email;
    @Comment("비밀번호")
    private String uPassword;
    @Comment("이름")
    private String uName;
    @Comment("닉네임")
    private String uNickname;
    @Comment("생년월일")
    private LocalDate uBirth;
}