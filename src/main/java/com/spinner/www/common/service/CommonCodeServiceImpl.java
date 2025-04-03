package com.spinner.www.common.service;

import com.spinner.www.common.entity.CommonCode;
import com.spinner.www.common.repository.CommonCodeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonCodeServiceImpl implements CommonCodeService {

    private final CommonCodeRepo commonCodeRepo;

    /**
     * codeIdx로 CommonCode 조회
     * @param codeIdx
     * @return
     */
    @Override
    public CommonCode getCommonCode(Long codeIdx) {
        return commonCodeRepo.findByCodeIdx(codeIdx);
    }

    /**
     * commonCode 파일 타입 정의
     * @param contentType String
     * @return CommonCode
     */
    @Override
    public CommonCode covContentType(String contentType) {

        if (contentType.startsWith("image/")) {
            return commonCodeRepo.findByCodeName("IMAGE");

        } else if (contentType.startsWith("video/")) {
            return commonCodeRepo.findByCodeName("VIDEO");

        } else if (contentType.equals("application/pdf")
                || contentType.equals("application/msword")
                || contentType.equals("application/vnd.ms-excel")
                || contentType.equals("application/vnd.ms-powerpoint")
                ) {
            return commonCodeRepo.findByCodeName("DOCUMENT");
        }
        return commonCodeRepo.findByCodeName("FILE");
    }
}
