package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.dto.user.UserRegistrationRequestDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.mapper.UserMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest { // TODO перенёс тест в правильное место. Допишите остальные тесты и тесты к остальным сервисам тоже.
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAuthenticatedUser() {
    }

    @Test
    void updateUserCredentials() {
    }

    @Test
    void setEmailConfirmation() {
    }

    @Test
    void createUser() {
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();
        requestDto.setUsername("testUser");
        requestDto.setEmail("test@example.com");

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        UserResponseDto userResponseDto = new UserResponseDto(1L, "test@example.com", "testUser", Role.ROLE_ADMIN, null);
        when(userMapper.toUser(any())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserResponseDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.createUser(requestDto);
        assertEquals(userResponseDto, result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser() {
    }
}