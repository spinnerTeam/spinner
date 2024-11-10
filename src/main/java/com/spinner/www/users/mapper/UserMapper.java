package com.spinner.www.users.mapper;

import com.spinner.www.users.dto.UserLoginDto;
import com.spinner.www.users.entity.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserLoginDto usersToUserLoginDTO(Users users);
}
