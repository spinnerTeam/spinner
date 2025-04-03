package com.spinner.www.common.service;

import com.spinner.www.common.entity.CommonCode;

public interface CommonCodeService {

    /**
     * codeIdx로 CommonCode 조회
     * @param codeIdx
     * @return
     */
    CommonCode getCommonCode(Long codeIdx);

    /**
     * 컨텐츠 타입 별 CommonCode 조회
     * @param contentType String
     * @return CommonCode
     */
    CommonCode covContentType(String contentType);
}
