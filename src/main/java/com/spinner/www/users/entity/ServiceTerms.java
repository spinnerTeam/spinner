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

import java.time.ZonedDateTime;

@Entity
@Table(name = "serviceTerms")
public class ServiceTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stIdx;
    private ZonedDateTime stDate;
    private StType stType;
    private boolean stConsent;

    @ManyToOne
    @JoinColumn(name = "uIdx")
    private Users user;

    private Mtype mtype;
    private boolean mConsent;
}
