package com.spinner.www.member.io;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SuperBuilder
@Data
@NoArgsConstructor
public class MemberProfileUpdateRequest {
    private String nickName;
    private String birth;
    // TODO 파일 쪽 확인 후 다시 작업
//    private MultipartFile file;
    private List<MemberInterestRequest> interests;
}
