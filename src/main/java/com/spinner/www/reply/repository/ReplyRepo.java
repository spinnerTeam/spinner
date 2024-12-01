package com.spinner.www.reply.repository;

import com.spinner.www.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyRepo extends JpaRepository<Reply, Long> {
    /**
     * 삭제되지 않은 reply 조회
     * @param replyIdx Long
     * @return Reply
     */
    Optional<Reply> findByReplyIdxAndReplyIsRemoved(Long replyIdx, int isRemove);
}
