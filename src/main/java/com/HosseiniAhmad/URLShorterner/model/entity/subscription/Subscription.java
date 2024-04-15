package com.HosseiniAhmad.URLShorterner.model.entity.subscription;

import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Данные о подписке пользователя
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@Entity
@Table(name = "subscriptions")
public class Subscription {
    /**
     * Идентификатор подписки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Дата начала подписки
     */
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    /**
     * Дата истечения подписки
     */
    @Builder.Default
    private LocalDate expiryDate = LocalDate.now().plusMonths(1L);
    /**
     * Признак того, что подписка оплачена
     */
    @Builder.Default
    private boolean isPaid = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

