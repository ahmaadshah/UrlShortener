package com.HosseiniAhmad.URLShorterner.dto.subscription;

import java.time.LocalDate;

public record SubscriptionResponseDto(
        Long id,
        LocalDate creationDate,
        LocalDate expiryDate,
        boolean isPaid,
        Long userId
) { }
