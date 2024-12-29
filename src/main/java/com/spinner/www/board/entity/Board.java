package com.spinner.www.board.entity;
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

    @Column(nullable = false)
    @Comment("공통코드 식별자")
    private Long codeIdx;

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

    @OneToMany(mappedBy = "boardIdx"
            ,fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();



    public void update(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }

    public void delete() {
        this.boardIsRemoved = 1;
    }
}