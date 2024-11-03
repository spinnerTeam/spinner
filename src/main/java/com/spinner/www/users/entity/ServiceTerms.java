package com.spinner.www.users.entity;

import com.spinner.www.constants.Mtype;
import com.spinner.www.constants.StType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import org.hibernate.annotations.Comment;

import java.time.ZonedDateTime;

@Entity
@Table(name = "serviceTerms")
@Comment("약관 테이블")
public class ServiceTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("약관PK")
    private Long stIdx;

    @Comment("이메일 수신 동의 or 앱 푸쉬 동의 ")
    private StType stType;
    @Comment("약관내용")
    private String stContent;

}
