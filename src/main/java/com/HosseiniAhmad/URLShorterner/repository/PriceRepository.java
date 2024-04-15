package com.HosseiniAhmad.URLShorterner.repository;

import com.HosseiniAhmad.URLShorterner.model.entity.bill.Price;
import com.HosseiniAhmad.URLShorterner.model.entity.subscription.Subscription;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    @Query("SELECT p FROM Price p WHERE p.creationDate <= CURRENT_DATE ORDER BY p.creationDate DESC")
    Price findLatestPriceBeforeToday(Limit limit);

    Price findByCreationDate(LocalDate date);
}

