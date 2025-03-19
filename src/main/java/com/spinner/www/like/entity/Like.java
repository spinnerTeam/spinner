package com.spinner.www.like.entity;

import com.spinner.www.board.entity.Board;
import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.entity.Member;
import com.spinner.www.reply.entity.Reply;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx", nullable = true)
    @Comment("게시글 식별자")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replyIdx", nullable = true)
    @Comment("댓글 식별자")
    private Reply reply;

    @ColumnDefault("1")
    @Comment("좋아요 여부")
    private int likeIsLiked;

    public void update() {
        this.likeIsLiked = this.likeIsLiked == 0 ? 1 : 0;
    }
}
