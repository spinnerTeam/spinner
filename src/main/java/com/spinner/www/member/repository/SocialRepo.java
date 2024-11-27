package com.spinner.www.member.repository;

import com.spinner.www.member.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialRepo extends JpaRepository<Social, Long> {

    /**
     * sub 유무
     * @param sub String
     * @return boolean
     */
    boolean existsBySocialNum(String sub);
}
