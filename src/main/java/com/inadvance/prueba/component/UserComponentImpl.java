package com.inadvance.prueba.component;

import com.inadvance.prueba.dto.UserDto;
import com.inadvance.prueba.exception.NotFoundException;
import com.inadvance.prueba.exception.ValidationException;
import com.inadvance.prueba.mapper.UserMapper;
import com.inadvance.prueba.model.User;
import com.inadvance.prueba.repository.UserRepository;
import com.inadvance.prueba.security.JWTAuthtenticationConfig;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserComponentImpl implements UserComponent {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Override
    public UserDto save(UserDto userDto) {

        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new ValidationException("El correo ya esta registrado: " + userDto.getEmail());
        }

        User userEntity = userMapper.userDtoToUserEntity(userDto);

        LocalDate now = LocalDate.now();
        userEntity.setCreated(now);
        userEntity.setModified(now);
        userEntity.setLastLogin(now);
        userEntity.setToken(jwtAuthtenticationConfig.getJWTToken(userDto.getName()));
        userEntity.setActive(true);

        userRepository.save(userEntity);

        return userMapper.userEntityToUserDto(userEntity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();

        return userList
                .stream()
                .map(user -> userMapper.userEntityToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userRequestDto) {

        Optional<User> user = userRepository.findByEmail(userRequestDto.getEmail());
        if (user.isPresent()) {
            User userEntity = userMapper.userDtoToUserEntity(userRequestDto);

            LocalDate now = LocalDate.now();
            userEntity.setCreated(user.get().getCreated());
            userEntity.setModified(now);
            userEntity.setLastLogin(now);
            userEntity.setToken(jwtAuthtenticationConfig.getJWTToken(userRequestDto.getName()));
            userRepository.save(userEntity);
            return userMapper.userEntityToUserDto(userEntity);
        } else {
            throw new NotFoundException("Usuario no encontrado con el mail: " + userRequestDto.getEmail());
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> userEntity = userRepository.findByEmail(email);
        if (userEntity.isPresent()) {
            return userMapper.userEntityToUserDto(userEntity.get());
        } else {
            throw new NotFoundException("Usuario no encontrado con el mail: " + email);
        }
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        Optional<User> userEntity = userRepository.findByEmail(email);
        if (userEntity.isPresent()) {;
            userRepository.delete(userEntity.get());
        } else {
            throw new NotFoundException("Usuario no encontrado con el mail: " + email);
        }
    }
}
