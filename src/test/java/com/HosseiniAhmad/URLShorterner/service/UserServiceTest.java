package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.dto.user.UserRegistrationRequestDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.mapper.UserMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import com.HosseiniAhmad.URLShorterner.util.UserHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @Mock
    private UserHolder userHolder; // Мокируем объект userHolder

    @InjectMocks
    private UserService userService; // Внедряем мокируемый объект в userService


    @BeforeEach
    void setUp() {
        Mockito.reset(userRepository);
    }


    @Test
    public void testCreateUser() {
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
    void getAuthenticatedUser() {
        // Arrange
        User authenticatedUser = new User();
        authenticatedUser.setId(1L);
        authenticatedUser.setUsername("testUser");
        authenticatedUser.setEmail("test@example.com");

        UserResponseDto userResponseDto = new UserResponseDto(1L, "test@example.com", "testUser", Role.ROLE_ADMIN, null);

        when(userHolder.getUserFromPrincipal()).thenReturn(authenticatedUser);
        when(userMapper.toUserResponseDto(authenticatedUser)).thenReturn(userResponseDto);

        // Act
        UserResponseDto result = userService.getAuthenticatedUser();

        // Assert
        assertEquals(userResponseDto, result);
    }


    @Test
    void setEmailConfirmation_SetsEmailConfirmation() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setRole(Role.ROLE_NOT_CONFIRMED);

        when(userRepository.getReferenceById(userId)).thenReturn(existingUser);

        // Act
        userService.setEmailConfirmation(userId);

        // Assert
        assertEquals(Role.ROLE_REGISTERED, existingUser.getRole());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void createUser() {
        // Arrange
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

        // Act
        UserResponseDto result = userService.createUser(requestDto);

        // Assert
        assertEquals(userResponseDto, result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser() {
        // Arrange
        User authenticatedUser = new User();
        authenticatedUser.setId(1L);
        authenticatedUser.setUsername("testUser");
        authenticatedUser.setEmail("test@example.com");
        authenticatedUser.setRole(Role.ROLE_DELETED);

        when(userHolder.getUserFromPrincipal()).thenReturn(authenticatedUser);

        // Act
        userService.deleteUser();

        // Assert
        assertEquals(Role.ROLE_DELETED, authenticatedUser.getRole());
        verify(userRepository, times(1)).save(authenticatedUser);
    }
}