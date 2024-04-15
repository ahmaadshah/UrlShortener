package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserUpdateRoleRequestDto;
import com.HosseiniAhmad.URLShorterner.mapper.UserMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.url.UrlBinding;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.UrlBindingRepository;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для панели администратора
 */
@Service
public class AdminPanelService {
    private final UserRepository userRepository;
    private final UrlBindingRepository urlBindingRepository;
    private final UserMapper userMapper;

    @Autowired
    public AdminPanelService(UserRepository userRepository, UrlBindingRepository urlBindingRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.urlBindingRepository = urlBindingRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDto> getUsers() {
        return userMapper.toUserResponseDtoList(userRepository.findAll());
    }

    /**
     * Метод получения пользователя по идентификатору
     *
     * @param id идентификатор пользователя
     * @return данные о пользователе
     */
    public UserResponseDto getUser(long id) {
        return userMapper.toUserResponseDto(userRepository.getReferenceById(id));
    }

    /**
     * Метод для админской панели и бизнес-процессов для изменения роли пользователя
     *
     * @param dto данные для обноления роли и/или статуса
     * @return изменённого пользователя
     */
    @Transactional
    public UserResponseDto updateUserRole(UserUpdateRoleRequestDto dto) {
        User toUpdate = userRepository.getReferenceById(dto.id());
        toUpdate.setRole(dto.newRole());
        return userMapper.toUserResponseDto(userRepository.save(toUpdate));
    }

    /**
     * Метод полного удаления данных пользователя. Применяется при отзыве разрешения на хранение персональных данных
     *
     * @param id идентификатор пользователя
     */
    @Transactional
    public void deleteUserCompletely(long id) {
        userRepository.deleteById(id);
    }

    /**
     * Метод полного удаления связки URL.
     *
     * @param id идентификатор связки URL
     * @see UrlBinding
     */
    @Transactional
    public void deleteUrlBindingCompletely(long id) {
        urlBindingRepository.deleteById(id);
    }
}
