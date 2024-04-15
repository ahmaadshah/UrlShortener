package com.HosseiniAhmad.URLShorterner.model.entity.url;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.HosseiniAhmad.URLShorterner.model.shortener.ShortenerConstants.SEPARATOR;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ShortUrl {
    /**
     * Базовая часть короткого URL
     */
    private String baseUrl;
    /**
     * Постоянный префикс пути короткого URL
     */
    private String path;
    /**
     * Уникальный суффикс пути короткого URL
     */
    private String uid;

    @Override
    public String toString() {
        return baseUrl + SEPARATOR + path + SEPARATOR + uid;
    }
}
