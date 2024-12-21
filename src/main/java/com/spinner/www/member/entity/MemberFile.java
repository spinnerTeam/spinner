package com.spinner.www.member.entity;

import com.spinner.www.file.entity.Files;
import jakarta.persistence.*;

@Entity
@Table(name = "member_file")
public class MemberFile {

    @Id
    @ManyToOne
    @JoinColumn(name = "memberIdx")
    private Member member;

    @Id
    @ManyToOne
    @JoinColumn(name = "fileIdx")
    private Files files;


}
