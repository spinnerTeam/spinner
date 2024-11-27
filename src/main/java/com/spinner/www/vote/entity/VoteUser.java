package com.spinner.www.vote.entity;

import com.spinner.www.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voteUserIdx")
    @Comment("투표_유저 Idx")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voteIdx")
    @Comment("투표 idx")
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voteItemIdx")
    @Comment("투표_항목 idx")
    private VoteItem voteItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx")
    @Comment("멤버 idx")
    private Member member;
}
