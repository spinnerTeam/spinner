package com.spinner.www.common.service;

import com.spinner.www.common.entity.CommonCode;

public interface CommonCodeService {

    /**
     * codeIdx로 CommonCode 조회
     * @param codeIdx
     * @return
     */
    CommonCode getComonCode(Long codeIdx);
}
