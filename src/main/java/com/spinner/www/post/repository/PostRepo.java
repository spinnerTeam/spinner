package com.spinner.www.post.repository;

import com.spinner.www.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    /**
     * 삭제되지 않은 post 조회
     * @param postIdx Long
     * @return Post
     */
    Optional<Post> findByPostIdxAndPostIsRemoved(Long postIdx, int isRemove);


}
