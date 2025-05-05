package com.spinner.www.profile.service;

import com.spinner.www.board.service.BoardService;
import com.spinner.www.common.io.CommonResponse;
import com.spinner.www.common.service.ServerInfo;
import com.spinner.www.constants.CommonResultCode;
import com.spinner.www.file.entity.Files;
import com.spinner.www.file.service.FileService;
import com.spinner.www.member.dto.SessionInfo;
import com.spinner.www.member.entity.Member;
import com.spinner.www.member.entity.MemberFile;
import com.spinner.www.member.io.MemberInterestRequest;
import com.spinner.www.member.io.MemberInterestResponse;
import com.spinner.www.member.io.MemberProfileResponse;
import com.spinner.www.member.io.MemberProfileUpdateRequest;
import com.spinner.www.member.service.MemberFileService;
import com.spinner.www.member.service.MemberInterestService;
import com.spinner.www.member.service.MemberService;
import com.spinner.www.util.ResponseVOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final BoardService boardService;
    private final SessionInfo sessionInfo;
    private final MemberService memberService;
    private final MemberFileService memberFileService;
    private final FileService fileService;
    private final MemberInterestService memberInterestService;
    private final ServerInfo serverInfo;
    /**
     * 내가 작성한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfMemberBoard(Long idx, int size) {
        return boardService.getSliceOfMemberBoard(idx,size);
    }

    /**
     * 내가 좋아요를 누른 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfLikedBoard(Long idx, int size) {
        return boardService.getSliceOfLikedBoard(idx,size);
    }

    /**
     * 북마크한 게시글 목록 조회
     * @param idx Long 조회 시작 idx
     * @param size int 조회할 목록 갯수
     * @return ResponseEntity<CommonResponse> 게시글 목록
     */
    @Override
    public ResponseEntity<CommonResponse> getSliceOfBookmarkedBoard(Long idx, int size) {
        return boardService.getSliceOfBookmarkedBoard(idx,size);
    }

    /**
     * 회원 프로필 조회
     * @return Member
     */
    public ResponseEntity<CommonResponse> getMemberProfile() {
        Long memberIdx = sessionInfo.getMemberIdx();
        if (Objects.isNull(memberIdx))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        Member member = memberService.getMember(memberIdx);
        if (Objects.isNull(member))
            return new ResponseEntity<>(ResponseVOUtils.getFailResponse(CommonResultCode.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);

        MemberFile memberFile = memberFileService.getMemberFile(member);

        Long fileIdx = null;
        if (!Objects.isNull(memberFile))
            fileIdx = memberFile.getFiles().getFileIdx();

        List<MemberInterestResponse> interestsResponses =memberInterestService.getAllMemberInterestResponses(member);


        MemberProfileResponse memberProfileResponse = MemberProfileResponse.builder()
                .nickName(member.getMemberNickname())
                .birth(member.getMemberBirth().toString())
                .profileImageUrl(Objects.isNull(fileIdx) ? null : serverInfo.getFullFileUrl()+fileIdx)
                .interests(interestsResponses)
                .build();

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(memberProfileResponse), HttpStatus.OK);
    }

    /**
     * 멤버 프로필 업데이트
     * @param request MemberProfileUpdateRequest
     * @param uploadFile MultipartFile
     * @return ResponseEntity<CommonResponse>
     */
    @Override
    public ResponseEntity<CommonResponse> updateMemberProfile(MemberProfileUpdateRequest request, MultipartFile uploadFile) throws IOException {
        Long memberIdx = sessionInfo.getMemberIdx();
        Member member = memberService.getMember(memberIdx);

        String nickName = request.getNickName();
        String birth = request.getBirth();
        List<MemberInterestRequest> interestRequests = request.getInterests();
        memberService.updateNicknameAndBirth(memberIdx, nickName, birth);
        memberInterestService.upsertMemberInterests(member, interestRequests);
        System.out.println(uploadFile);
        if (Objects.isNull(uploadFile)) {
            return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
        }

        MemberFile memberFile = memberFileService.getMemberFile(member);
        if (!Objects.isNull(memberFile)) {
            memberFileService.delete(memberFile);
        }

        Files file = fileService.uploadAndSaveFile(uploadFile);

        memberFileService.create(member, file);

        return new ResponseEntity<>(ResponseVOUtils.getSuccessResponse(), HttpStatus.OK);
    }

}
