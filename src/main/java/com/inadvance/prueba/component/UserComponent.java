package com.inadvance.prueba.component;

import com.inadvance.prueba.dto.UserDto;

import java.util.List;

public interface UserComponent {
    UserDto save(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserDto userRequestDto);

    void deleteUser(String email);

    UserDto getUserByEmail(String email);
}
