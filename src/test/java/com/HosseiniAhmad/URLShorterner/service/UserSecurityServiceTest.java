package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserSecurityServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSecurityService userSecurityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Инициализация userRepository для userSecurityService
        userSecurityService = new UserSecurityService(userRepository);
    }

    @Test
    void loadUserByUsername_SuccessfulLoad() {
        // Arrange
        String username = "testUser";
        User user = new User(); // Создайте пользователя с ожидаемыми данными
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        // Проверьте ожидаемые данные пользователя в userDetails
    }

    @Test
    void getUserByUsername_ReturnsUserIfExists() {
        // Arrange
        String username = "testUser";
        User expectedUser = new User(); // Создайте ожидаемого пользователя
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userSecurityService.getUserByUsername(username);

        // Assert
        assertTrue(result.isPresent()); // Убедитесь, что Optional содержит значение
        assertEquals(expectedUser, result.get()); // Убедитесь, что возвращенный пользователь совпадает с ожидаемым пользователем
    }

    @Test
    void getUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonexistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userSecurityService.getUserByUsername(username);

        // Assert
        assertFalse(result.isPresent()); // Убедитесь, что Optional пуст
    }


    @Test
    void getUserByEmail_ReturnsUserIfExists() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User(); // Создайте ожидаемого пользователя
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<User> result = userSecurityService.getUserByEmail(email);

        // Assert
        assertTrue(result.isPresent()); // Убедитесь, что Optional содержит значение
        assertEquals(expectedUser, result.get()); // Убедитесь, что возвращенный пользователь совпадает с ожидаемым пользователем
    }

    @Test
    void getUserByEmail_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userSecurityService.getUserByEmail(email);

        // Assert
        assertFalse(result.isPresent()); // Убедитесь, что Optional пуст
    }

}