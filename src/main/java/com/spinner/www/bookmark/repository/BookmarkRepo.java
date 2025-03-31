package com.spinner.www.bookmark.repository;

import com.spinner.www.board.entity.Board;
import com.spinner.www.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepo extends JpaRepository<Bookmark, Long> {

    /**
     * 북마크 조회
     * @param board Board
     * @return Bookmark
     */
    List<Bookmark> findByBoard(Board board);
}
