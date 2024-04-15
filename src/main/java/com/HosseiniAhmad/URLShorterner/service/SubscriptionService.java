package com.HosseiniAhmad.URLShorterner.service;

import com.HosseiniAhmad.URLShorterner.dto.subscription.SubscriptionResponseDto;
import com.HosseiniAhmad.URLShorterner.exception.SubscriptionException;
import com.HosseiniAhmad.URLShorterner.mapper.SubscriptionMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.subscription.Subscription;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.repository.SubscriptionRepository;
import com.HosseiniAhmad.URLShorterner.repository.UserRepository;
import com.HosseiniAhmad.URLShorterner.service.payment.paypal.PayPalService;
import com.HosseiniAhmad.URLShorterner.service.payment.PaymentSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final PaymentSystemService paymentSystemService;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository, SubscriptionMapper subscriptionMapper, PayPalService paymentSystemService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.paymentSystemService = paymentSystemService;
    }

    public SubscriptionResponseDto getSubscription(Long id) {
        return subscriptionMapper.toSubscriptionResponseDto(subscriptionRepository.getReferenceById(id));
    }

    public Set<SubscriptionResponseDto> getSubscriptionsByUserId(Long userId) {
        return subscriptionMapper.toSubscriptionResponseDtoSet(subscriptionRepository.findUserActiveSubscriptions(userId));
    }

    @Transactional
    public SubscriptionResponseDto createSubscription(Long userId) {
        User user = userRepository.getReferenceById(userId);
        Subscription subscription = Subscription.builder().user(user).build();
        user.addSubscription(subscription);
        subscription = subscriptionRepository.save(subscription); // сохранение user выполнится по каскаду
        return subscriptionMapper.toSubscriptionResponseDto(subscription);
    }

    public String getSubscriptionPaymentLink(Long subscriptionId) {
        return paymentSystemService.getPaymentLink(subscriptionId);
    }

    @Transactional
    public SubscriptionResponseDto setSubscriptionIsPaid(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.getReferenceById(subscriptionId);
        if (!paymentSystemService.isPaid(subscriptionId))
            throw new SubscriptionException("Subscription has not been paid");
        subscription.setPaid(true);
        subscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toSubscriptionResponseDto(subscription);
    }

    /**
     * Устанавливает актуальные даты ранее неоплаченной подписке
     * @param subscriptionId идентификатор подписки
     * @return новое состояние подписки
     */
    @Transactional
    public SubscriptionResponseDto updateSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.getReferenceById(subscriptionId);
        subscription.setCreationDate(LocalDate.now());
        subscription.setExpiryDate(subscription.getCreationDate().plusMonths(1L));
        subscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toSubscriptionResponseDto(subscription);
    }

    @Transactional
    public void deleteUnpaidSubscription(Long id) {
        Subscription subscription = subscriptionRepository.getReferenceById(id);
        if (subscription.isPaid()) throw new SubscriptionException("Cannot delete paid subscription");
        subscription.getUser().removeSubscription(subscription);
        subscriptionRepository.delete(subscription);
    }
}

// TODO сделать шедулер для периодической проверки истекающих подписок и оповещения пользователей о необходимости продлегния