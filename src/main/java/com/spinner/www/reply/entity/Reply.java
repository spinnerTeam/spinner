package com.spinner.www.reply.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.List;

@Entity
@Table(name = "reply")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("댓글 테이블")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("댓글 pk")
    private Long replyIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx", nullable = false)
    @Comment("유저 식별자")
    private Member member;

    @Column(nullable = false)
    @Comment("게시글 식별자")
    private Long boardIdx;

    @Column(nullable = false)
    @Comment("내용")
    private String replyContent;

    @ColumnDefault("0")
    @Comment("삭제여부")
    private int replyIsRemoved;

    @Column()
    @Comment("부모 댓글 식별자")
    private Long replyParentIdx;

    @OneToMany(mappedBy = "replyParentIdx",
            fetch = FetchType.LAZY)
    private List<Reply> childReplies;

    public void update(String replyContent) {
        this.replyContent = replyContent;
    }

    public void delete() {
        this.replyIsRemoved = 1;
    }
}
