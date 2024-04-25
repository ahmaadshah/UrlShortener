package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.mapper.UserMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.UrlBindingRepository;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class AdminPanelServiceTest {
    @Mock
    private UrlBindingRepository urlBindingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AdminPanelService adminPanelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers_ReturnsListOfUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.toUserResponseDtoList(userList)).thenReturn(new ArrayList<>());

        // Act
        List<UserResponseDto> result = adminPanelService.getUsers();

        // Assert
        assertEquals(0, result.size());
    }

    @Test
    void getUser_ReturnsUserDetails() {
        // Arrange
        long userId = 1L;
        User user = new User();
        // Assuming UserResponseDto constructor requires user details
        UserResponseDto userResponseDto = new UserResponseDto(userId, "username", "email", Role.USER, new HashSet<>());
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(userMapper.toUserResponseDto(user)).thenReturn(userResponseDto);

        // Act
        UserResponseDto result = adminPanelService.getUser(userId);

        // Assert
        assertNotNull(result);
        // Additional assertions if needed to verify user details in the result
    }


    @Test
    void deleteUserCompletely_DeletesUser() {
        // Arrange
        long userId = 1L;

        // Act
        adminPanelService.deleteUserCompletely(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void deleteUrlBindingCompletely_DeletesUrlBinding() {
        // Arrange
        long urlBindingId = 1L;

        // Act
        adminPanelService.deleteUrlBindingCompletely(urlBindingId);

        // Assert
        verify(urlBindingRepository, times(1)).deleteById(urlBindingId);
    }
}