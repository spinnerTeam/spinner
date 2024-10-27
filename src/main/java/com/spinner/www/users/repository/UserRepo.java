package com.spinner.www.users.repository;

import com.spinner.www.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    /**
     *  user 이메일 조회
     * @param uEmail String
     * @return boolean
     */
    boolean existsByUEmail(String uEmail);
}
