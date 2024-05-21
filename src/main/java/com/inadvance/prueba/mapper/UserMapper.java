package com.inadvance.prueba.mapper;

import com.inadvance.prueba.dto.UserDto;
import com.inadvance.prueba.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PhoneMapper.class, componentModel = "spring")
public interface UserMapper {

    UserDto userEntityToUserDto(UserEntity userEntity);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "token", ignore = true)
    UserEntity userDtoToUserEntity(UserDto userDto);
}
