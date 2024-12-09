package com.spinner.www.vote.repository;

import com.spinner.www.vote.entity.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteItemRepo extends JpaRepository<VoteItem, Long> {
}
