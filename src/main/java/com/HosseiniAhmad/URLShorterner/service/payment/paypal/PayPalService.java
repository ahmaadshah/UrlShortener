package com.HosseiniAhmad.URLShorterner.service.payment.paypal;

import com.HosseiniAhmad.URLShorterner.dto.payment.ErrorTokenResponse;
import com.HosseiniAhmad.URLShorterner.dto.payment.PayPalTokenRequest;
import com.HosseiniAhmad.URLShorterner.dto.payment.PayPalTokenResponse;
import com.HosseiniAhmad.URLShorterner.exception.PaymentException;
import com.HosseiniAhmad.URLShorterner.service.payment.PaymentSystemService;
import com.HosseiniAhmad.URLShorterner.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Сервис отвечает за взаимодействие с платёжными системами
 */
@Service
public class PayPalService implements PaymentSystemService {

    private final PayPalProperties payPalProperties;
    private final RestClient restClient;
    private PayPalTokenResponse token;

    @Autowired
    public PayPalService(PayPalProperties payPalProperties) {
        this.payPalProperties = payPalProperties;
        restClient = RestClient.create(this.payPalProperties.getBaseUrl());
    }

    public void updateToken() {
        if (token != null && !token.isExpired()) return;

        token = restClient.post()
                .uri(payPalProperties.getTokenPath())
                .header(
                        HttpHeaders.AUTHORIZATION,
                        WebUtil.getBasicAuthHeader(payPalProperties.getClientId(), payPalProperties.getSecret())
                )
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(WebUtil.getFormUrlEncodedBody(PayPalTokenRequest.builder().build(), PayPalTokenRequest.class))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), (request, response) -> {
                    throw new PaymentException("Cannot get PayPal token for request %s", response, request);
                })
                .body(PayPalTokenResponse.class);
    }

    // TODO дописать

    @Override
    public boolean isPaid(Long someId) {
        return true;
    }

    @Override
    public String getPaymentLink(Long someId) {
        return String.format("/api/subscription/%s/bought", someId);
    }
    //TODO пока это просто заглушка. В будущем планируется интеграция с PayPal https://developer.paypal.com/api/rest/

}
