package com.inadvance.prueba.service;

import com.inadvance.prueba.component.UserComponentImpl;
import com.inadvance.prueba.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.inadvance.prueba.util.UserHelper.isValidEmail;
import static com.inadvance.prueba.util.UserHelper.isValidPassword;

@Service
public class UserService {

    @Autowired
    private UserComponentImpl userComponent;

    @Value("${regex.password}")
    private String passwordRegex;

    public UserDto createUser(UserDto userDto) {
        isValidEmail(userDto.getEmail());
        isValidPassword(passwordRegex, userDto.getPassword());
        return userComponent.save(userDto);
    }

    public List<UserDto> getAllUsers() {
        return userComponent.getAllUsers();
    }

    public UserDto updateUser(UserDto userDto) {
        isValidEmail(userDto.getEmail());
        isValidPassword(passwordRegex, userDto.getPassword());
        return userComponent.updateUser(userDto);
    }

    public UserDto getUserByEmail(String email) {
        isValidEmail(email);
        return userComponent.getUserByEmail(email);
    }


    public void deleteUser(String email) {
        isValidEmail(email);
        userComponent.deleteUser(email);
    }

}
