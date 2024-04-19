package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.subscription.Subscription;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SubscriptionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    void findByUser_Id() {
        // Создаем пользователя и подписку
        User user = new User();
        entityManager.persist(user);
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        entityManager.persist(subscription);
        entityManager.flush();

        // Ищем подписку по ID пользователя
        Subscription foundSubscription = subscriptionRepository.findByUser_Id(user.getId());

        // Проверяем, что подписка найдена и соответствует ожидаемой
        assertNotNull(foundSubscription);
        assertEquals(subscription, foundSubscription);
    }

    @Test
    void findUserActiveSubscriptions() {
        // Создаем пользователя
        User user = new User();
        entityManager.persist(user);
        entityManager.flush();

        // Создаем активные и неактивные подписки для пользователя
        Subscription activeSubscription1 = new Subscription();
        activeSubscription1.setUser(user);
        activeSubscription1.setExpiryDate(LocalDate.now().plusDays(1));
        entityManager.persist(activeSubscription1);

        Subscription activeSubscription2 = new Subscription();
        activeSubscription2.setUser(user);
        activeSubscription2.setExpiryDate(LocalDate.now().plusDays(2));
        entityManager.persist(activeSubscription2);

        Subscription inactiveSubscription = new Subscription();
        inactiveSubscription.setUser(user);
        inactiveSubscription.setExpiryDate(LocalDate.now().minusDays(1));
        entityManager.persist(inactiveSubscription);

        entityManager.flush();

        // Ищем активные подписки для пользователя
        Set<Subscription> activeSubscriptions = subscriptionRepository.findUserActiveSubscriptions(user.getId());

        // Проверяем, что найдены только активные подписки и их количество соответствует ожидаемому
        assertEquals(2, activeSubscriptions.size());
        assertTrue(activeSubscriptions.contains(activeSubscription1));
        assertTrue(activeSubscriptions.contains(activeSubscription2));
        assertFalse(activeSubscriptions.contains(inactiveSubscription));
    }
}
