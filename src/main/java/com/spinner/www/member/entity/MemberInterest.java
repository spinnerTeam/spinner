package com.spinner.www.member.entity;

import com.spinner.www.common.entity.StudyTopic;
import com.spinner.www.member.dto.MemberInterestCreateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "memberInterest")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Comment("관심분야")
public class MemberInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("관심분야 PK")
    private Long memberInterestIdx;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "menuIdx")
    private StudyTopic studyTopic;

    @Comment("관심분야 선택 여부")
    private boolean memberIsSelected;

    public static MemberInterest insertMemberInterest(MemberInterestCreateDto memberInterestCreateDto){
        return MemberInterest.builder()
                .member(memberInterestCreateDto.getMember())
                .studyTopic(memberInterestCreateDto.getStudyTopic())
                .memberIsSelected(true)
                .build();
    }

    public void updateMemberIsSelected(boolean isSelected){
        this.memberIsSelected = isSelected;
    }
}
