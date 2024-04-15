package com.HosseiniAhmad.URLShorterner.model.entity.url;

import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static com.HosseiniAhmad.URLShorterner.util.CommonUtil.MAX_DATE;

/**
 * Класс, хранящий связь длинного и короткого URL, количество переходов на коротки URL и ссылку на пользователя,
 * которому принадлежит связь
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@Entity
@Table(name = "url_binding")
public class UrlBinding {
    /**
     * Идентификатор связи длинного и короткого URL
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Исходная (длинная) URL
     */
    private String longUrl;
    /**
     * Укороченная URL
     */
    @Embedded
    private ShortUrl shortUrl;
    /**
     * Счетчик переходов
     */
    @Builder.Default
    private int clicks = 0;
    /**
     * Дата создания связи
     */
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    /**
     * Дата истечения связи
     */
    @Builder.Default
    private LocalDate expiryDate = MAX_DATE;

    /**
     * Является ли связь закрытой
     */
    @Builder.Default
    private boolean isClosed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}

