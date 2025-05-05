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
    @Comment("신고 종류")
    private ReportType reportType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporterMemberIdx")
    @Comment("신고자")
    private Member reporterMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportedBoardIdx")
    @Comment("신고 대상 사용자")
    private Board reportedBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reportedMemberIdx")
    @Comment("신고 대상 게시글")
    private Member reportedMember;

    @Comment("신고 삭제 여부")
    private String reportIsRemoved;

    protected Report() {}

    /**
     * 생성 메서드
     */
    public static Report create(ReportType reportType, Member reporterMember, Member reportedMember) {
        return  Report.builder()
                .reportType(reportType)
                .reporterMember(reporterMember)
                .reportedMember(reportedMember)
                .reportIsRemoved("N")
                .build();
    }

    public static Report create(ReportType reportType, Member reporterMember, Board reportedBoard) {
        return  Report.builder()
                .reportType(reportType)
                .reporterMember(reporterMember)
                .reportedBoard(reportedBoard)
                .reportIsRemoved("N")
                .build();
    }
}
