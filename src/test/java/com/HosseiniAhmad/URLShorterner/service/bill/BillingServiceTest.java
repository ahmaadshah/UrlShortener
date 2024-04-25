package com.HosseiniAhmad.URLShorterner.service.bill;

import com.HosseiniAhmad.URLShorterner.exception.BillingException;
import com.HosseiniAhmad.URLShorterner.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BillingServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private BillingService billingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void create_CreatesNewPrice() {
        // Arrange
        BigDecimal newPriceValue = new BigDecimal("15.99");

        // Act
        assertDoesNotThrow(() -> billingService.create(newPriceValue));

        // Assert
        verify(priceRepository, times(1)).save(any());
    }

    @Test
    void create_ThrowsExceptionIfNewPriceIsNegative() {
        // Arrange
        BigDecimal negativePriceValue = new BigDecimal("-10.99");

        // Act and Assert
        assertThrows(BillingException.class, () -> billingService.create(negativePriceValue));
    }
}