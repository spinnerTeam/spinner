package com.spinner.www.vote.repository;

import com.spinner.www.board.entity.Board;
import com.spinner.www.vote.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {

    @Query("SELECT v FROM Vote v" +
            " JOIN FETCH v.voteItems vi" +
            " WHERE v.board = :board" +
            " AND v.voteIsRemoved = 'N'" +
            " AND vi.voteItemIsRemoved = 'N'")
    List<Vote> findVotesByBoard(@Param("board") Board board);
}
