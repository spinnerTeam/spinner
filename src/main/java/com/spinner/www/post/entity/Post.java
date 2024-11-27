package com.spinner.www.post.entity;
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
@Table(name = "post")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("게시판 테이블")

public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("게시판 PK")
    private Long postIdx;

    @ManyToOne
    @JoinColumn(name = "memberIdx", nullable = false)
    @Comment("유저 식별자")
    private Member member;
    @Column(nullable = false)
    @Comment("유저 식별자")
    private Long memberIdx;
    @Column(nullable = false)
    @Comment("제목")
    private String postTitle;
    @Column(nullable = false)
    @Comment("내용")
    private String postContent;
    @ColumnDefault("0")
    @Comment("삭제여부")
    private int postIsRemoved;
    @ColumnDefault("0")
    @Comment("신고여부")
    private int postIsReported;


    public void update(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }

    public void delete() {
        this.postIsRemoved = 1;
    }
}