package com.spinner.www.vote.repository;

import com.spinner.www.vote.entity.VoteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteUserRepo extends JpaRepository<VoteUser, Long> {
}
