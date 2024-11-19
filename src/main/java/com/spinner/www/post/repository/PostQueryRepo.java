package com.spinner.www.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spinner.www.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.spinner.www.post.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class PostQueryRepo {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Post> getSliceOfPost(
            @Nullable
            Long idx,
            int size,
            @Nullable
            String keyword
    ) {
        return jpaQueryFactory.selectFrom(post)
                .where(ltPostIdx(idx), search(keyword))
                .orderBy(post.postIdx.desc())
                .limit(size)
                .fetch();
    }

    @SuppressWarnings("all")
    private BooleanBuilder search(String keyword) {
        return containsTitle(keyword).or(containsContent(keyword));
    }

    private BooleanBuilder containsTitle(@Nullable String title) {
        System.out.println(StringUtils.hasText(title));
        return StringUtils.hasText(title) ? new BooleanBuilder(post.postTitle.contains(title)) : new BooleanBuilder();
    }

    private BooleanBuilder containsContent(@Nullable String content) {
        return StringUtils.hasText(content) ? new BooleanBuilder(post.postContent.contains(content)) : new BooleanBuilder();
    }

    private BooleanBuilder ltPostIdx(@Nullable Long idx) {
        return idx == null ? new BooleanBuilder() : new BooleanBuilder(post.postIdx.lt(idx));
    }
}
