package com.spinner.www.users.repository;

import com.spinner.www.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    /**
     *  user 이메일 조회
     * @param email String
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * user 객체 조회
     * @param email String
     * @return User
     */
    Users findByEmail(String email);
}
