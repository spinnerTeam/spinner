package com.spinner.www.users.mapper;

import com.spinner.www.users.dto.UserLoginDto;
import com.spinner.www.users.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserLoginDto usersToUserLoginDTO(Member member);
}
