package com.spinner.www.vote.entity;

import com.spinner.www.common.entity.BaseEntity;
import com.spinner.www.post.entity.Post;
import com.spinner.www.vote.dto.VoteCreateDto;
import com.spinner.www.vote.dto.VoteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

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
    private Post post;

    /*
    @OneToMany(mappedBy = "vote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VoteItem> voteItem = new ArrayList<>();
    */

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
     * 생성 메서드
     */
    public static Vote create(Post post, VoteCreateDto voteCreateDto) {
        return Vote.builder()
                .post(post)
                .voteName(voteCreateDto.getVoteName())
                .voteStatus(voteCreateDto.getVoteStatus())
                .voteType(voteCreateDto.getVoteType())
                .voteIsRemoved("N")
                .startDatetime(voteCreateDto.getVoteStartDatetime())
                .endDatetime(voteCreateDto.getVoteEndDatetime())
                .build();
    }

    /**
     * 수정 메서드
     */
    public void update(VoteDto voteDto) {
        this.id = voteDto.getVoteId();
        this.voteName = voteDto.getVoteName();
    }

    /**
     * 삭제 메서드
     */
    public void softDelete(VoteDto voteDto) {
        this.id = voteDto.getVoteId();
        this.voteIsRemoved = "Y";
    }
}
