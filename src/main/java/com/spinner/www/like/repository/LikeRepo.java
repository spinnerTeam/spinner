package com.spinner.www.like.repository;

import com.spinner.www.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {

    /**
     * 좋아요 조회
     * @param boardIdx Long
     * @return Like
     */
    Optional<Like> findByBoardIdx(Long boardIdx);

    /**
     * 좋아요 조회
     * @param replyIdx Long
     * @return Like
     */
    Optional<Like> findByReplyIdx(Long replyIdx);
}
