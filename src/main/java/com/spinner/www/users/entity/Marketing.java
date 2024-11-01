package com.spinner.www.users.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
