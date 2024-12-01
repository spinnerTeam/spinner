package com.spinner.www.post.service;

import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.io.SearchParamRequest;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.post.dto.PostGetDto;
import com.spinner.www.post.entity.Post;
import com.spinner.www.post.io.PostCreateRequest;
import com.spinner.www.post.io.PostListResponse;
import com.spinner.www.post.io.PostResponse;
import com.spinner.www.post.io.PostUpdateRequest;
import com.spinner.www.post.mapper.PostMapper;
import com.spinner.www.post.repository.PostQueryRepo;
import com.spinner.www.post.repository.PostRepo;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final SessionInfo sessionInfo;
    private final PostRepo postRepo;
    private final PostQueryRepo postQueryRepo;
    private final MemberService memberService;
    private final PostMapper postMapper;
    /**
     * 게시글 생성
     * @param postRequest PostCreateRequestIO 게시글 요청 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> insert(PostCreateRequest postRequest) {
        Long memberIdx = sessionInfo.getMemberIdx();

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);

        Post post = Post.builder()
                .member(member)
                .postTitle(postRequest.getTitle())
                .postContent(postRequest.getContent())
                .build();

        postRepo.save(post);
        PostResponse response = PostResponse.builder()
                .nickName(member.getMemberNickname())
                .idx(post.getPostIdx())
                .title(post.getPostTitle())
                .content(post.getPostContent())
                .createdDate(post.getCreatedDate())
                .modifiedDate(post.getModifiedDate())
                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(response), HttpStatus.CREATED);
    }

    /**
     * 게시글 uuid로 삭제되지 않은 게시글 조회
     * @param postIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public Post findByPostIdx(Long postIdx) {
        int isNotRemove = 0;
        return postRepo.findByPostIdxAndPostIsRemoved(postIdx, isNotRemove).orElse(null);
    }

    /**
     * 게시글 조회
     * @param postIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    public ResponseEntity<CommonResponse> findByPostInfo(Long postIdx) {
        Post post= this.findByPostIdx(postIdx);

        if (Objects.isNull(post))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        PostGetDto dto = postMapper.postToPostGetDto(post);
//        PostResponse response = PostResponse.builder()
//                                .nickName(post.getMember().getMemberNickname())
//                                .idx(post.getPostIdx())
//                                .title(post.getPostTitle())
//                                .content(post.getPostContent())
//                                .createdDate(post.getCreatedDate())
//                                .modifiedDate(post.getModifiedDate())
//                                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(dto), HttpStatus.OK);
    }

    /**
     * 게시글 목록 조회
     * @param searchRequest SearchParamRequest 검색 조건
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfPost(SearchParamRequest searchRequest) {
        List<PostListResponse> list = this.postQueryRepo.getSliceOfPost(searchRequest.getIdx(),
                                                                        searchRequest.getSize(),
                                                                        searchRequest.getKeyword())
                                                                        .stream().map(PostListResponse::new).collect(Collectors.toList());
        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(list), HttpStatus.OK);
    }


    /**
     * 게시글 수정
     * @param postIdx Long 게시글 idx
     * @param postRequest PostUpdateRequestIO 게시글 수정 데이터
     * @return ResponseEntity<CommonResponse> 게시글 상세 정보
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> update(Long postIdx, PostUpdateRequest postRequest) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);
        Post post= this.findByPostIdx(postIdx);

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(post))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(post.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        post.update(postRequest.getPostTitle(), postRequest.getPostContent());

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(post), HttpStatus.OK);
    }

    /**
     * 게시글 삭제
     * @param postIdx Long 게시글 idx
     * @return ResponseEntity<CommonResponse> 삭제 응답 결과
     */
    @Override
    @Transactional
    public ResponseEntity<CommonResponse> delete(Long postIdx) {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);
        Post post= this.findByPostIdx(postIdx);

        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        if (Objects.isNull(post))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);

        if (!Objects.equals(post.getMember(), member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.FORBIDDEN), HttpStatus.FORBIDDEN);

        post.delete();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }
}
