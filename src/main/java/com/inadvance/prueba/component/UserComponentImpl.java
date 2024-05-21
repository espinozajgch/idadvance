package com.inadvance.prueba.component;

import com.inadvance.prueba.dto.UserDto;
import com.inadvance.prueba.exception.NotFoundException;
import com.inadvance.prueba.mapper.PhoneMapper;
import com.inadvance.prueba.mapper.UserMapper;
import com.inadvance.prueba.model.UserEntity;
import com.inadvance.prueba.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.inadvance.prueba.util.UserHelper.generateToken;

@Component
public class UserComponentImpl implements UserComponent {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PhoneMapper phoneMapper;

    @Override
    public UserDto save(UserDto userDto) {

        Optional<UserEntity> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new NotFoundException("Ya existe un usuario registrado con el mail: " + userDto.getEmail());
        }

        UserEntity userEntity = userMapper.userDtoToUserEntity(userDto);

        LocalDate now = LocalDate.now();
        userEntity.setCreated(now);
        userEntity.setModified(now);
        userEntity.setLastLogin(now);
        userEntity.setToken(generateToken());
        userEntity.setActive(true);

        userEntity.getPhones().forEach(phonedbo -> {
            phonedbo.setUser(userEntity);
        });

        userRepository.save(userEntity);

        return userMapper.userEntityToUserDto(userEntity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();

        return userEntityList
                .stream()
                .map(user -> userMapper.userEntityToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userRequestDto) {

        Optional<UserEntity> user = userRepository.findByEmail(userRequestDto.getEmail());
        if (user.isPresent()) {
            UserEntity userEntity = userMapper.userDtoToUserEntity(userRequestDto);

            LocalDate now = LocalDate.now();
            userEntity.setCreated(user.get().getCreated());
            userEntity.setModified(now);
            userEntity.setLastLogin(now);
            userEntity.setToken(generateToken());
            userRepository.save(userEntity);
            return userMapper.userEntityToUserDto(userEntity);
        } else {
            throw new NotFoundException("Usuario no encontrado con el mail: " + userRequestDto.getEmail());
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isPresent()) {
            return userMapper.userEntityToUserDto(userEntity.get());
        } else {
            throw new NotFoundException("Usuario no encontrado con el mail: " + email);
        }
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isPresent()) {;
            userRepository.delete(userEntity.get());
        } else {
            throw new NotFoundException("Usuario no encontrado con el mail: " + email);
        }
    }
}
