package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.bill.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;


    @Test
    void findLatestPriceBeforeToday() {
        // Создание тестовой цены
        Price price = new Price();
        price.setCreationDate(LocalDate.now().minusDays(1)); // Задаем дату вчерашнюю

        // Сохранение цены в репозитории
        priceRepository.save(price);

        // Определение объекта Pageable с ограничением на один результат
        Pageable pageable = PageRequest.of(0, 1);

        // Поиск последней цены перед сегодняшней датой
        Price latestPrice = priceRepository.findLatestPriceBeforeToday(pageable.toLimit());

        // Проверка, что цена найдена и её дата соответствует ожидаемой
        assertNotNull(latestPrice);
        assertEquals(LocalDate.now().minusDays(1), latestPrice.getCreationDate());
    }


    @Test
    void findByCreationDate() {
        // Создание тестовой цены
        Price price = new Price();
        price.setCreationDate(LocalDate.now());

        // Сохранение цены в репозитории
        priceRepository.save(price);

        // Поиск цены по дате создания
        Price foundPrice = priceRepository.findByCreationDate(LocalDate.now());

        // Проверка, что цена найдена и её дата соответствует ожидаемой
        assertNotNull(foundPrice);
        assertEquals(LocalDate.now(), foundPrice.getCreationDate());
    }

    @Test
    void findByCreationDate_NotFound() {
        // Поиск цены по несуществующей дате создания
        Price foundPrice = priceRepository.findByCreationDate(LocalDate.now());

        // Проверка, что цена не найдена
        assertNull(foundPrice);
    }
}
