package com.spinner.www.report.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.member.entity.Member;
import com.spinner.www.board.entity.Board;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Report extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportIdx")
    @Comment("신고 PK")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportTypeIdx")
    @Comment("reportTypeIdx")
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx")
    @Comment("boardIdx")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberIdx")
    @Comment("memberIdx")
    private Member member;

    @Comment("신고 삭제 여부")
    private String reportIsRemoved;

    protected Report() {}

    /**
     * 생성 메서드
     */
    public static Report create(ReportType reportType, Board board, Member member) {
        return  Report.builder()
                .reportType(reportType)
                .board(board)
                .member(member)
                .reportIsRemoved("N")
                .build();
    }
}
