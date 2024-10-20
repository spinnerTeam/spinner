package com.spinner.www.users.entity;

import com.spinner.www.constants.Stype;
import jakarta.persistence.*;

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
