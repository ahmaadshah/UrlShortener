package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.url.ShortUrl;
import com.HosseiniAhmad.URLShorterner.model.entity.url.UrlBinding;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UrlBindingRepositoryTest {

    @Autowired
    private UrlBindingRepository urlBindingRepository;


    @Test
    void findByShortUrlUid() {
        // Создание тестового ShortUrl
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setUid("testShortUrlUid");

        // Создание тестового UrlBinding
        UrlBinding urlBinding = new UrlBinding();
        urlBinding.setShortUrl(shortUrl);

        // Сохранение UrlBinding в репозитории
        urlBindingRepository.save(urlBinding);

        // Поиск UrlBinding по ShortUrl
        Optional<UrlBinding> foundUrlBinding = urlBindingRepository.findByShortUrl_Uid("testShortUrlUid");

        // Проверка, что найден UrlBinding и его ShortUrl соответствует ожидаемому
        assertTrue(foundUrlBinding.isPresent());
        assertEquals(shortUrl, foundUrlBinding.get().getShortUrl());
    }
}
