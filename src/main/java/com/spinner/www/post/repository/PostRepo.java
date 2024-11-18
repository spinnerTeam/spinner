package com.spinner.www.post.repository;

import com.spinner.www.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

    /**
     * 삭제되지 않은 post 조회
     * @param postIdx Long
     * @return Post
     */
    Post findByPostIdxAndPostIsRemoved(Long postIdx, int isRemove);


}
