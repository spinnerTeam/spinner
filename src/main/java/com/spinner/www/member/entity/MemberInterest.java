package com.spinner.www.member.entity;

import com.spinner.www.common.entity.Menu;
import com.spinner.www.member.dto.MemberInterestCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "memberInterest")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("관심분야")
public class MemberInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("관심분야 PK")
    private Long memberInterestIdx;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "menuIdx")
    private Menu menu;

    public static MemberInterest insertMemberInterest(MemberInterestCreateDto memberInterestCreateDto){
        return MemberInterest.builder()
                .member(memberInterestCreateDto.getMember())
                .menu(memberInterestCreateDto.getMenu())
                .build();
    }
}
