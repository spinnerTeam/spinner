package com.spinner.www.like.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "likes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("좋아요 테이블")
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("좋아요 pk")
    private Long likeIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx", nullable = false)
    @Comment("유저 식별자")
    private Member member;

    @Column(nullable = true)
    @Comment("게시글 식별자")
    private Long boardIdx;

    @Column(nullable = true)
    @Comment("댓글 식별자")
    private Long replyIdx;

    @ColumnDefault("1")
    @Comment("좋아요 여부")
    private int likeIsLiked;

    public void update() {
        this.likeIsLiked = this.likeIsLiked == 0 ? 1 : 0;
    }
}
