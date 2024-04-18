package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.subscription.Subscription;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @BeforeEach
    void setUp() {
        // Добавьте код для настройки тестовых данных перед каждым тестом, если это необходимо
    }

    @AfterEach
    void tearDown() {
        // Добавьте код для очистки данных после каждого теста, если это необходимо
    }

    @Test
    void findByUserId() {
        // Создание тестового пользователя
        User user = new User();
        user.setId(1L);

        // Создание тестовой подписки
        Subscription subscription = new Subscription();
        subscription.setUser(user); // Предположим, что у пользователя с id=1 есть подписка

        // Сохранение подписки в репозитории
        subscriptionRepository.save(subscription);

        // Поиск подписки по ID пользователя
        Subscription foundSubscription = subscriptionRepository.findByUser_Id(1L);

        // Проверка, что подписка найдена
        assertNotNull(foundSubscription);

        // Проверка, что ID пользователя в найденной подписке соответствует ожидаемому
        assertEquals(1L, foundSubscription.getUser().getId());
    }

}
