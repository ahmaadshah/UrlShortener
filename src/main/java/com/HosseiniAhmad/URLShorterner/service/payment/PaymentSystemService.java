package com.HosseiniAhmad.URLShorterner.service.payment;

/**
 * Сервис отвечает за взаимодействие с платёжными системами
 */
public interface PaymentSystemService {
    /**
     * Метод для проверки, что оплата прошла
     * @param someId
     * @return true, если оплата выполнена
     */
    boolean isPaid(Long someId);

    /**
     * Метод для подготовки платёжной системы
     *
     * @param someId
     * @return ссылку на страницу платёжной системы в текстовом виде
     */
    String getPaymentLink(Long someId);
}
