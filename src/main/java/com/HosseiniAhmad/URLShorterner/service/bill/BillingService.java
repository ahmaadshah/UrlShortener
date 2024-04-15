package com.HosseiniAhmad.URLShorterner.service.bill;

import com.HosseiniAhmad.URLShorterner.exception.BillingException;
import com.HosseiniAhmad.URLShorterner.model.entity.bill.Price;
import com.HosseiniAhmad.URLShorterner.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Сервис, предоставляющий цену на подписку
 */
@Service
public class BillingService {
    private final PriceRepository priceRepository;

    @Autowired
    public BillingService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /**
     * Метод полчения цены подписки на месяц
     * @return актуальная цена подписки на месяц
     */
    public Price geCurrentPrice() {
        return priceRepository.findLatestPriceBeforeToday(Limit.of(1));
    }

    /**
     * Метод создания цены подписки
     * @param newPrice новая стоимость подписки
     */
    @Transactional
    public void create(BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) < 0)
            throw new BillingException("New prise should be not negative");
        Price price = Price.builder().price(newPrice).build();
        priceRepository.save(price);
    }

    // TODO сделать шедулер для получения значений инфляции и пересчёта стоимости
}
