package com.spinner.www.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "marketing")
public class Marketing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mIdx;

    @ManyToOne
    @JoinColumn(name = "stIdx")
    private ServiceTerms serviceTerms;

    private ZonedDateTime mDate;

    @ManyToOne
    @JoinColumn(name = "uIdx")
    private Users users;
}
