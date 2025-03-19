package com.spinner.www.common.repository;

import com.spinner.www.common.entity.CommonCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonCodeRepo extends JpaRepository<CommonCode, Long> {

    /**
     * 공통코드 인덱스로 조회
     * @param codeIdx
     * @return CommonCode
     */
    CommonCode findByCodeIdx(Long codeIdx);
}
