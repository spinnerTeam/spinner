package com.spinner.www.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uIdx;

    @ManyToOne
    @JoinColumn(name = "roIdx")
    private UserRole userRole;

    private String uEmail;
    private String uPw;
    private String uName;
    private String uNickname;
    private LocalDate uBirth;
}