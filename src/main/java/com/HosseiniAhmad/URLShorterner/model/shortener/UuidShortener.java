package com.HosseiniAhmad.URLShorterner.model.shortener;

import com.HosseiniAhmad.URLShorterner.model.entity.url.ShortUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.HosseiniAhmad.URLShorterner.model.shortener.ShortenerConstants.SHORTENER_PATH_PREFIX;

/**
 * Реализация класса, сокращающего ссылки. Данная реалиация сокращает ссылки на основе генерируемого UUID
 *
 * @see UrlShortener
 * @see UUID
 */
@Component
public class UuidShortener implements UrlShortener {
    @Value("${shortener.target-path-length}")
    private Integer targetPathLength;
    @Value("${shortener.base-url}")
    private String baseUrl;

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortUrl getShortUrl(String original) {
        return new ShortUrl(baseUrl, SHORTENER_PATH_PREFIX, UUID.randomUUID().toString().substring(0, targetPathLength));
    }
}
