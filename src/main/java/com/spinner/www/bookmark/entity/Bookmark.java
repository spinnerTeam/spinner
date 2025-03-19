package com.spinner.www.bookmark.entity;

import com.spinner.www.board.entity.Board;
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
@Table(name = "bookmarks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("북마크 테이블")
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("북마크 pk")
    private Long bookmarkIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx", nullable = false)
    @Comment("유저")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx", nullable = false)
    @Comment("게시글")
    private Board board;

    @ColumnDefault("1")
    @Comment("북마크 여부")
    private int isBookmarked;

    public void update() {
        this.isBookmarked = this.isBookmarked == 0 ? 1 : 0;
    }
}
