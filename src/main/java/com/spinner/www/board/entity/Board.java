package com.spinner.www.board.entity;
import com.spinner.www.bookmark.entity.Bookmark;
import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.common.entity.CommonCode;
import com.spinner.www.like.entity.Like;
import com.spinner.www.member.entity.Member;
import com.spinner.www.reply.entity.Reply;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("게시판 테이블")

public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("게시판 PK")
    private Long boardIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codeIdx", nullable = false)
    @Comment("공통코드 식별자")
    private CommonCode commonCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx", nullable = false)
    @Comment("유저 식별자")
    private Member member;

    @Column(nullable = false)
    @Comment("제목")
    private String boardTitle;

    @Column(nullable = false)
    @Comment("내용")
    private String boardContent;

    @ColumnDefault("0")
    @Comment("삭제여부")
    private int boardIsRemoved;

    @ColumnDefault("0")
    @Comment("신고여부")
    private int boardIsReported;

    @ColumnDefault("0")
    @Comment("조회수")
    @Column(nullable = false)
    private Long hitCount;

    @OneToMany(mappedBy = "board"
            ,fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "board"
            ,fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board"
            ,fetch = FetchType.LAZY)
    private List<Bookmark> bookmarks = new ArrayList<>();

    public void update(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public void increaseHitCount() {
        this.hitCount += 1;
    }

    public void delete() {
        this.boardIsRemoved = 1;
    }
}