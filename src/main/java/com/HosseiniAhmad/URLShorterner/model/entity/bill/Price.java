package com.HosseiniAhmad.URLShorterner.model.entity.bill;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Данные о стоимости месячной подписки на текущую дату
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "prices")
public class Price {
    /**
     * Идентификатор стоимости
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Дата действия цены
     */
    @Builder.Default
    @Column(name = "creation_date", unique = true)
    private LocalDate creationDate = LocalDate.now();

    /**
     * Стоимость в евро
     */
    private BigDecimal price;
}
