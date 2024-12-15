package com.spinner.www.member.service;


import com.spinner.www.member.constants.RoleName;
import com.spinner.www.member.entity.MemberRole;
import com.spinner.www.member.repository.MemberRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberRoleServiceImpl implements MemberRoleService{

    private final MemberRoleRepo memberRoleRepo;

    @Override
    public MemberRole getRole(RoleName roleName) {
        return memberRoleRepo.findByRoleName(roleName);
    }
}
