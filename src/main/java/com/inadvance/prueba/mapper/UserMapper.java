package com.inadvance.prueba.mapper;

import com.inadvance.prueba.dto.UserDto;
import com.inadvance.prueba.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userEntityToUserDto(User user);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "token", ignore = true)
    User userDtoToUserEntity(UserDto userDto);
}
