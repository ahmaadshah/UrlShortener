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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @Mock
    private UrlShortener urlShortener;

    @Mock
    private UrlBindingRepository urlBindingRepository;

    @Mock
    private UrlBindingMapper urlBindingMapper;

    @Mock
    private UserHolder userHolder;

    @InjectMocks
    private UrlService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createBinding_ThrowsExceptionIfSubscriptionIsExpired() {
        // Arrange
        UrlBindingCreateRequestDto requestDto = new UrlBindingCreateRequestDto();
        requestDto.setLongUrl("https://example.com");

        User user = new User();
        user.setId(1L);
        user.setSubscriptions(new HashSet<>()); // Expired subscription
        when(userHolder.getUserFromPrincipal()).thenReturn(user);

        // Act and Assert
        assertThrows(SubscriptionException.class, () -> urlService.createBinding(requestDto));
    }


    @Test
    void getByUid_ThrowsExceptionIfUrlBindingNotFound() {
        // Arrange
        String uid = "abc123";
        when(urlBindingRepository.findByShortUrl_Uid(uid)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> urlService.getByUid(uid));
    }

    @Test
    void getUserBindings_ReturnsUserBindings() {
        // Arrange
        User user = new User();
        user.setId(1L);
        when(userHolder.getUserFromPrincipal()).thenReturn(user);
        when(urlBindingRepository.findUrlBindings(user.getId())).thenReturn(new HashSet<>());
        when(urlBindingMapper.toUrlBindingResponseDtoSet(any())).thenReturn(new HashSet<>());

        // Act
        Set<UrlBindingResponseDto> result = urlService.getUserBindings();

        // Assert
        assertNotNull(result);
        // Additional assertions if needed
    }

    @Test
    void getUserActiveBindings_ReturnsUserActiveBindings() {
        // Arrange
        User user = new User();
        user.setId(1L);
        when(userHolder.getUserFromPrincipal()).thenReturn(user);
        when(urlBindingRepository.findActiveUrlBindings(user.getId())).thenReturn(new HashSet<>());
        when(urlBindingMapper.toUrlBindingResponseDtoSet(any())).thenReturn(new HashSet<>());

        // Act
        Set<UrlBindingResponseDto> result = urlService.getUserActiveBindings();

        // Assert
        assertNotNull(result);
        // Additional assertions if needed
    }


    @Test
    void closeBinding_ClosesUrlBinding() {
        // Arrange
        Long id = 1L;
        UrlBinding urlBinding = new UrlBinding();
        when(urlBindingRepository.getReferenceById(id)).thenReturn(urlBinding);

        // Act
        urlService.closeBinding(id);

        // Assert
        assertTrue(urlBinding.isClosed());
        verify(urlBindingRepository, times(1)).save(urlBinding);
    }
}
