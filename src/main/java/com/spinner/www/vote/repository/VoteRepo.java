package com.spinner.www.vote.repository;

import com.spinner.www.post.entity.Post;
import com.spinner.www.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {

    List<Vote> findByPost(Post post);
}
