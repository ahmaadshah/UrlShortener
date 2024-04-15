package com.HosseiniAhmad.URLShorterner.model.entity.user;

public enum Role {
    /**
     * Пользователь зарегистрировался, но не подтвердил свой email
     */
    ROLE_NOT_CONFIRMED,
    /**
     * Зарегистрированный пользователь с подтверждённым email
     */
    ROLE_REGISTERED,
    /**
     * Заблокированный пользователь
     */
    ROLE_BLOCKED,
    /**
     * Удалённая учётная запись
     */
    ROLE_DELETED,
    /**
     * Администратор системы
     */
    ROLE_ADMIN,
    /**
     * Техническая учётка для интеграций
     */
    ROLE_TECHNICAL
}
