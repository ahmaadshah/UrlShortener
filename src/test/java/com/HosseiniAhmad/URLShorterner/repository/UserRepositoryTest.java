package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void findByUsername() {
        // Создание тестового пользователя
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        // Сохранение пользователя в репозитории
        userRepository.save(user);

        // Поиск пользователя по имени пользователя
        Optional<User> foundUser = userRepository.findByUsername("testUser");

        // Проверка, что найден пользователь и его имя пользователя соответствует ожидаемому
        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void findByEmail() {
        // Создание тестового пользователя
        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        // Сохранение пользователя в репозитории
        userRepository.save(user);

        // Поиск пользователя по адресу электронной почты
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        // Проверка, что найден пользователь и его адрес электронной почты соответствует ожидаемому
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }
}
