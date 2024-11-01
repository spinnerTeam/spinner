package com.spinner.www.users.entity;

import com.spinner.www.constants.Stype;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "social")
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sIdx;
    @ManyToOne
    @JoinColumn(name = "uIdx")
    private Users users;

    private Stype stype;
    private String Snum;
}
