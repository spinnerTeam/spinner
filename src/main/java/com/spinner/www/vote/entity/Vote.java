package com.spinner.www.vote.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.board.entity.Board;
import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.dto.VoteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voteIdx")
    @Comment("투표 idx")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postIdx")
    @Comment("게시물 idx")
    private Board board;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VoteItem> voteItems = new ArrayList<>();

    @Comment("투표 제목")
    private String voteName;

    @Enumerated(EnumType.STRING)
    @Comment("투표 상태: [ing, multiple, end]")
    private VoteStatus voteStatus;

    @Enumerated(EnumType.STRING)
    @Comment("투표 타입: [community, study]")
    private VoteType voteType;

    @Comment("투표 삭제 여부")
    private String voteIsRemoved;

    @Comment("투표 시작 일자")
    private LocalDateTime startDatetime;

    @Comment("투표 마감 일자")
    private LocalDateTime endDatetime;


    /**
     * 연관관계 메서드 설정
     * @param voteItem VoteItem
     */
    public void addVoteItem(VoteItem voteItem) {
        this.voteItems.add(voteItem);
    }

    /**
     * 생성 메서드
     */
    public static Vote create(Board board, VoteCreateDto voteCreateDto) {
        LocalDateTime endDatetime = voteCreateDto.getVoteEndDatetime() != null
                ? voteCreateDto.getVoteEndDatetime()
                : LocalDateTime.of(9999, 12, 31, 23, 59, 59);
        // 마감 기한 설정 없을 시 EndDateTime 9999-12-31 23:59:59 설정

        return Vote.builder()
                .board(board)
                .voteName(voteCreateDto.getVoteName())
                .voteStatus(voteCreateDto.getVoteStatus())
                .voteType(voteCreateDto.getVoteType())
                .voteIsRemoved("N")
                .startDatetime(LocalDateTime.now())
                .endDatetime(endDatetime)
                .build();
    }

    /**
     * 투표 내용 수정 메서드
     */
    public void update(VoteDto voteDto) {
        this.id = voteDto.getVoteIdx();
        this.voteName = voteDto.getVoteName();
    }

    /**
     * 투표 삭제 메서드
     */
    public void softDelete(VoteDto voteDto) {
        this.id = voteDto.getVoteIdx();
        this.voteIsRemoved = "Y";
    }

    /**
     * 투표 즉시 마감 메서드
     */
    public void statusUpdate(VoteDto voteDto) {
        this.id = voteDto.getVoteIdx();
        this.voteStatus = VoteStatus.end;
    }
}
