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
    public CommonCode getComonCode(Long codeIdx) {
        return commonCodeRepo.findByCodeIdx(codeIdx);
    }
}
