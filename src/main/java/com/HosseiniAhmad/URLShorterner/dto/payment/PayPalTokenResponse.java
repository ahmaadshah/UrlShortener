package com.HosseiniAhmad.URLShorterner.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Объект с данными о токене, выданном после аутентификации в PayPal
 * <br>Пример
 * <pre>
 * {
 *   "scope": "https://uri.paypal.com/services/invoicing https://uri.paypal.com/services/disputes/read-buyer https://uri.paypal.com/services/payments/realtimepayment https://uri.paypal.com/services/disputes/update-seller https://uri.paypal.com/services/payments/payment/authcapture openid https://uri.paypal.com/services/disputes/read-seller https://uri.paypal.com/services/payments/refund https://api-m.paypal.com/v1/vault/credit-card https://api-m.paypal.com/v1/payments/.* https://uri.paypal.com/payments/payouts https://api-m.paypal.com/v1/vault/credit-card/.* https://uri.paypal.com/services/subscriptions https://uri.paypal.com/services/applications/webhooks",
 *   "access_token": "A21AAFEpH4PsADK7qSS7pSRsgzfENtu-Q1ysgEDVDESseMHBYXVJYE8ovjj68elIDy8nF26AwPhfXTIeWAZHSLIsQkSYz9ifg",
 *   "token_type": "Bearer",
 *   "app_id": "APP-80W284485P519543T",
 *   "expires_in": 31668,
 *   "nonce": "2020-04-03T15:35:36ZaYZlGvEkV4yVSz8g6bAKFoGSEzuy3CQcz3ljhibkOHg"
 * }
 * </pre>
 * @param scope эндпоинты, доступные для работы с направленным токеном
 * @param accessToken токен доступа
 * @param tokenType тип токена (обычно Bearer)
 * @param appId идентификатор приложения, зарегистрированного в PayPal для работы через API
 * @param expiresIn время жизни токена в секундах
 * @param nonce дата и время получения токена в UTC с добавленным идентификатором
 */
public record PayPalTokenResponse(
        @JsonProperty("scope") String scope,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("app_id") String appId,
        @JsonProperty("expires_in") Long expiresIn,
        @JsonProperty("nonce") String nonce
) {
    public boolean isExpired() {
        Instant start = Instant.parse(nonce.substring(0, nonce.indexOf("Z")));
        return start.isBefore(start.plus(expiresIn, ChronoUnit.SECONDS));
    }
}
