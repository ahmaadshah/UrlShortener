package com.HosseiniAhmad.URLShorterner.service.payment.paypal;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Настройки взаимодействия с PayPal
 */
@Data
@ConfigurationProperties(prefix = "shortener.payment.paypal")
public class PayPalProperties {
    private String clientId;
    private String secret;
    private String baseUrl;
    private String tokenPath;
}
