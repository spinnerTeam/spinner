package com.spinner.www.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "userRole")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roIdx;
    private String roName;
}
