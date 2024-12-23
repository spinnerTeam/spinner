package com.spinner.www.board.repository;

import com.spinner.www.board.entity.Board;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepo extends JpaRepository<Board, Long> {

    /**
     * 삭제되지 않은 board 조회
     * @param boardIdx Long
     * @return Board
     */
    @EntityGraph(attributePaths = {"member", "replies"})
    Optional<Board> findByBoardIdxAndBoardIsRemoved(Long boardIdx, int isRemove);


}
