package com.spinner.www.member.repository;

import com.spinner.www.member.constants.RoleName;
import com.spinner.www.member.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleRepo extends JpaRepository<MemberRole, Integer> {

    MemberRole findByRoleName(RoleName roleName);
}
