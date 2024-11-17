package com.spinner.www.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Table(name = "member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("유저 테이블")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("유저PK")
    private Long memberIdx;

    @ManyToOne
    @JoinColumn(name = "roIdx")
    @Comment("유저 권한")
    private MemberRole memberRole;
    @Comment("이메일")
    private String memberEmail;
    @Comment("비밀번호")
    private String memberPassword;
    @Comment("이름")
    private String memberName;
    @Comment("닉네임")
    private String memberNickname;
    @Comment("생년월일")
    private LocalDate memberBirth;
}