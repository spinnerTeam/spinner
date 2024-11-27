package com.spinner.www.vote.entity;

import com.spinner.www.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voteItemIdx")
    @Comment("투표_항목 idx")
    private Long id;

    private String voteItemName;

    private String voteItemStatus;
}
