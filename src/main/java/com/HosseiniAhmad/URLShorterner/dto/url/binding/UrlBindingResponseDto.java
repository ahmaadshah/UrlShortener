package com.HosseiniAhmad.URLShorterner.dto.url.binding;

import java.time.LocalDate;

public record UrlBindingResponseDto(
        Long id,
        String shortUrl,
        String longUrl,
        int clicks,
        LocalDate creationDate,
        LocalDate expiryDate,
        boolean isClosed,
        Long userId
) { }
