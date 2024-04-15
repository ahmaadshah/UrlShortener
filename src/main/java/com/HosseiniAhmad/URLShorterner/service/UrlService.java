package com.HosseiniAhmad.URLShorterner.service;


import com.HosseiniAhmad.URLShorterner.dto.url.binding.UrlBindingCreateRequestDto;
import com.HosseiniAhmad.URLShorterner.dto.url.binding.UrlBindingResponseDto;
import com.HosseiniAhmad.URLShorterner.exception.SubscriptionException;
import com.HosseiniAhmad.URLShorterner.mapper.UrlBindingMapper;
import com.HosseiniAhmad.URLShorterner.model.entity.url.ShortUrl;
import com.HosseiniAhmad.URLShorterner.model.entity.url.UrlBinding;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.model.shortener.UrlShortener;
import com.HosseiniAhmad.URLShorterner.repository.UrlBindingRepository;
import com.HosseiniAhmad.URLShorterner.util.UserHolder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class UrlService {
    private final UrlShortener urlShortener;
    private final UrlBindingRepository urlBindingRepository;
    private final UrlBindingMapper urlBindingMapper;
    private final UserHolder userHolder;

    @Autowired
    public UrlService(UrlShortener urlShortener, UrlBindingRepository urlBindingRepository, UrlBindingMapper urlBindingMapper, UserHolder userHolder) {
        this.urlShortener = urlShortener;
        this.urlBindingRepository = urlBindingRepository;
        this.urlBindingMapper = urlBindingMapper;
        this.userHolder = userHolder;
    }

    /**
     * Метод сокращения длинного URL. Если данный пользователь уже сокращал такой URL, то запись будет обновлена
     * (запись открыта и дата истечения обновлена)
     *
     * @param requestDto запрос на создание связи исходного URL и короткого URL
     * @return короткий URL, при переходе на который выполняется редирект исходный URL
     */
    public UrlBindingResponseDto createBinding(UrlBindingCreateRequestDto requestDto) {
        User user = userHolder.getUserFromPrincipal();
        if (user.getSubscriptions().stream().noneMatch(s -> s.isPaid() && s.getExpiryDate().isAfter(LocalDate.now())))
            throw new SubscriptionException("User %d doesn't have an actual subscription", user.getId());

        ShortUrl shortUrl = urlShortener.getShortUrl(requestDto.getLongUrl());
        UrlBinding.UrlBindingBuilder bindingBuilder = UrlBinding.builder()
                .shortUrl(shortUrl)
                .longUrl(requestDto.getLongUrl())
                .user(user);
        Optional.ofNullable(requestDto.getExpiryDate()).ifPresent(bindingBuilder::expiryDate);
        UrlBinding binding = bindingBuilder.build();
        user.addUrlBinding(binding);
        return urlBindingMapper.toUrlBindingResponseDto(urlBindingRepository.save(binding));
    }

    /**
     * Метод помогает находить связь URL-ов по короткой ссылке. Используется для редиректа на длинный URL
     * При каждом использовании данного метода считается, что кто-то перешёл по ссылке
     *
     * @param uid уникальный суффикс пути короткого URL
     * @return данные о связи короткого и длинного URL
     */
    public UrlBindingResponseDto getByUid(String uid) {
        UrlBinding urlBinding = urlBindingRepository.findByShortUrl_Uid(uid)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find binding by uid " + uid));
        urlBinding.setClicks(urlBinding.getClicks() + 1);
        return urlBindingMapper.toUrlBindingResponseDto(urlBindingRepository.save(urlBinding));
    }

    /**
     * Метод получения списка связей короткого и длинного URL-ов у аутентифицированного пользователя
     *
     * @return список связей URL-ов
     */
    public Set<UrlBindingResponseDto> getUserBindings() {
        User user = userHolder.getUserFromPrincipal();
        return urlBindingMapper.toUrlBindingResponseDtoSet(urlBindingRepository.findUrlBindings(user.getId()));
    }

    /**
     * Метод получения списка активных (не закрытых и актуальных) связей короткого и длинного URL-ов
     * у аутентифицированного пользователя
     *
     * @return список связей URL-ов
     */
    public Set<UrlBindingResponseDto> getUserActiveBindings() {
        User user = userHolder.getUserFromPrincipal();
        return urlBindingMapper.toUrlBindingResponseDtoSet(urlBindingRepository.findActiveUrlBindings(user.getId()));
    }

    public void closeBinding(Long id) {
        UrlBinding urlBinding = urlBindingRepository.getReferenceById(id);
        urlBinding.setClosed(true);
        urlBindingRepository.save(urlBinding);
    }
}
