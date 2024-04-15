package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.dto.user.UserRegistrationRequestDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserUpdateCredentialsRequestDto;
import com.HosseiniAhmad.URLShorterner.mapper.UserMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import com.HosseiniAhmad.URLShorterner.util.CommonUtil;
import com.HosseiniAhmad.URLShorterner.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserHolder userHolder;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, UserHolder userHolder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userHolder = userHolder;
    }

    /**
     * Получаем данные о пользователе, под которым прошли аутентификацию
     *
     * @return данные пользователя
     */
    @Transactional
    public UserResponseDto getAuthenticatedUser() {
        return userMapper.toUserResponseDto(userHolder.getUserFromPrincipal());
    }

    /**
     * С помощью метода пользователь может изменить свои креды. Метод также используется в панели админа, поэтому в
     * UserUpdateCredentialsRequestDto явно передаётся id пользователя
     *
     * @param dto данные об изменении кредов пользователя
     * @return изменённого пользователя
     */
    @Transactional
    public UserResponseDto updateUserCredentials(UserUpdateCredentialsRequestDto dto) {
        User toUpdate = userRepository.getReferenceById(dto.id());
        if (CommonUtil.setIfChanged(dto.email(), toUpdate::setEmail)) // при изменении email нужно снова подтвердить пользователя
            toUpdate.setRole(Role.ROLE_NOT_CONFIRMED);
        CommonUtil.setIfChanged(dto.password(), toUpdate::setPassword);
        CommonUtil.setIfChanged(dto.username(), toUpdate::setUsername);
        return userMapper.toUserResponseDto(userRepository.save(toUpdate));
    }

    @Transactional
    public void setEmailConfirmation(Long id) {
        User toUpdate = userRepository.getReferenceById(id);
        toUpdate.setRole(Role.ROLE_REGISTERED);
        userMapper.toUserResponseDto(userRepository.save(toUpdate));
    }

    @Transactional
    public UserResponseDto createUser(UserRegistrationRequestDto candidate) {
        return userMapper.toUserResponseDto(userRepository.save(userMapper.toUser(candidate)));
    }

    @Transactional
    public void deleteUser() {
        User user = userHolder.getUserFromPrincipal();
        user.setRole(Role.ROLE_DELETED);
        userRepository.save(user);
    }
}
