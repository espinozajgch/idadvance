package com.inadvance.prueba.mapper;

import com.inadvance.prueba.dto.PhoneDto;
import com.inadvance.prueba.model.PhoneEntity;
import com.inadvance.prueba.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(source = "user.id", target = "user_id")
    PhoneDto phoneEntityToPhoneDto(PhoneEntity phoneEntity);

    @Mapping(source = "user_id", target = "user")
    PhoneEntity phoneDtoToPhoneEntity(PhoneDto phoneDto);

    default UserEntity mapUUIDToUserEntity(UUID userId) {
        if (userId == null) {
            return null;
        }
        UserEntity user = new UserEntity();
        user.setId(userId);
        return user;
    }

}
