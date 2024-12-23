package com.spinner.www.member.entity;

import com.spinner.www.file.entity.Files;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

@Entity
@Table(name = "member_file")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MemberFile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("멤버파일 PK")
    private Long memberFileIdx;

    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member member;


    @ManyToOne
    @JoinColumn(name = "fileIdx")
    private Files files;

    public static MemberFile insertMemberFile(Member member, Files files){
        return MemberFile.builder()
                .member(member)
                .files(files)
                .build();
    }
}
