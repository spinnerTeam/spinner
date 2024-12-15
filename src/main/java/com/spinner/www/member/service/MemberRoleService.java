package com.spinner.www.member.service;

import com.spinner.www.member.constants.RoleName;
import com.spinner.www.member.entity.MemberRole;

public interface MemberRoleService {

    MemberRole getRole(RoleName roleName);
}
