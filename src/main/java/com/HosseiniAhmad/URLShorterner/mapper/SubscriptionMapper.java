package com.HosseiniAhmad.URLShorterner.mapper;

import com.HosseiniAhmad.URLShorterner.dto.subscription.SubscriptionResponseDto;
import com.HosseiniAhmad.URLShorterner.model.entity.subscription.Subscription;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SubscriptionMapper {

    public SubscriptionResponseDto toSubscriptionResponseDto(Subscription subscription) {
        return new SubscriptionResponseDto(
                subscription.getId(),
                subscription.getCreationDate(),
                subscription.getExpiryDate(),
                subscription.isPaid(),
                subscription.getUser().getId()
        );
    }

    public Set<SubscriptionResponseDto> toSubscriptionResponseDtoSet(Set<Subscription> subscriptions) {
        return subscriptions.stream().map(this::toSubscriptionResponseDto).collect(Collectors.toSet());
    }
}
