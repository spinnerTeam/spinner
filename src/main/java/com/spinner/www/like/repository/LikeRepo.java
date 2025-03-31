package com.spinner.www.like.repository;

import com.spinner.www.board.entity.Board;
import com.spinner.www.like.entity.Like;
import com.spinner.www.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {

    /**
     * 좋아요 조회
     * @param board Board
     * @return Like
     */
    List<Like> findByBoard(Board board);

    /**
     * 좋아요 조회
     * @param reply Reply
     * @return Like
     */
    List<Like> findByReply(Reply reply);
}
