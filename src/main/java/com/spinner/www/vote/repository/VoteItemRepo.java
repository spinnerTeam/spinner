package com.spinner.www.vote.repository;

import com.spinner.www.vote.entity.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteItemRepo extends JpaRepository<VoteItem, Long> {
}
