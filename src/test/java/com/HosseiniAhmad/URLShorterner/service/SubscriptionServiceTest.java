package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.dto.subscription.SubscriptionResponseDto;
import com.HosseiniAhmad.URLShorterner.mapper.SubscriptionMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.subscription.Subscription;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.SubscriptionRepository;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import com.HosseiniAhmad.URLShorterner.service.payment.PaymentSystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;

    @Mock
    private PaymentSystemService paymentSystemService;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getSubscription() {
        // Arrange
        Long subscriptionId = 1L;
        LocalDate creationDate = LocalDate.now();
        LocalDate expiryDate = LocalDate.now().plusMonths(1);
        boolean isPaid = false;
        Long userId = 1L;
        // Создание экземпляра SubscriptionResponseDto с необходимыми аргументами
        SubscriptionResponseDto subscriptionResponseDto = new SubscriptionResponseDto(
                subscriptionId, creationDate, expiryDate, isPaid, userId);
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setCreationDate(creationDate);
        subscription.setExpiryDate(expiryDate);
        subscription.setPaid(isPaid);
        User user = new User();
        user.setId(userId);
        subscription.setUser(user);

        when(subscriptionRepository.getReferenceById(subscriptionId)).thenReturn(subscription);
        when(subscriptionMapper.toSubscriptionResponseDto(subscription)).thenReturn(subscriptionResponseDto);

        // Act
        SubscriptionResponseDto result = subscriptionService.getSubscription(subscriptionId);

        // Assert
        assertNotNull(result);
        assertEquals(subscriptionId, result.id());
        assertEquals(creationDate, result.creationDate());
        assertEquals(expiryDate, result.expiryDate());
        assertEquals(isPaid, result.isPaid());
        assertEquals(userId, result.userId());
    }


    @Test
    void createSubscription() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        Subscription subscription = Subscription.builder().user(user).build();
        SubscriptionResponseDto expectedResponseDto = new SubscriptionResponseDto(subscription.getId(), LocalDate.now(), LocalDate.now().plusMonths(1), false, userId);

        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);
        when(subscriptionMapper.toSubscriptionResponseDto(subscription)).thenReturn(expectedResponseDto);

        // Act
        SubscriptionResponseDto result = subscriptionService.createSubscription(userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedResponseDto, result);
        verify(userRepository, times(1)).getReferenceById(userId);
        verify(subscriptionRepository, times(1)).save(subscription);
        verify(subscriptionMapper, times(1)).toSubscriptionResponseDto(subscription);
    }


    @Test
    void deleteUnpaidSubscription() {
        // Arrange
        Long subscriptionId = 1L;
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionId);
        subscription.setPaid(false);
        User user = new User();
        user.addSubscription(subscription);

        when(subscriptionRepository.getReferenceById(subscriptionId)).thenReturn(subscription);
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        // Act
        subscriptionService.deleteUnpaidSubscription(subscriptionId);

        // Assert
        assertFalse(user.getSubscriptions().contains(subscription));
        verify(subscriptionRepository, times(1)).delete(subscription);
    }


}
