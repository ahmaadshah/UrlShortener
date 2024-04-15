package com.HosseiniAhmad.URLShorterner.model.shortener;

import com.HosseiniAhmad.URLShorterner.model.entity.url.ShortUrl;

/**
 * Интерфейс, который должны имплементировать классы, скоращающие ссылки
 */
public interface UrlShortener {
    /**
     * Метод сокращения ссылки
     * @param original исходная длиннаяя сслыка
     * @return короткую ссылку
     */
    ShortUrl getShortUrl(String original);
}
