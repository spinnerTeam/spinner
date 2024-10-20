package com.spinner.www.entity;

import com.spinner.www.constants.Mtype;
import com.spinner.www.constants.StType;
import jakarta.persistence.*;

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
