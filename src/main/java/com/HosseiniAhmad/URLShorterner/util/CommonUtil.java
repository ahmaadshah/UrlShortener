package com.HosseiniAhmad.URLShorterner.util;

import com.HosseiniAhmad.URLShorterner.dto.common.ChangeFieldStatus;
import com.HosseiniAhmad.URLShorterner.dto.common.ChangedField;

import java.time.LocalDate;
import java.util.function.Consumer;

public class CommonUtil {
    private CommonUtil() {
    }

    /**
     * Максимальное значение даты, которое можно хранить во всех базах данных. Оно меньше, чем LocalDate.max()
     */
    public static final LocalDate MAX_DATE = LocalDate.of(2999,12,31);

    /**
     * Метод для работы с изменёнными полями
     *
     * @param newValue новое значение изменённого поля
     * @param setter   сеттер для установки нового значения
     * @param <T>      тип изменяемого поля
     * @return true, если было установлено новое значение
     * @see com.HosseiniAhmad.URLShorterner.dto.common.ChangedField
     */
    public static <T> boolean setIfChanged(ChangedField<T> newValue, Consumer<T> setter) {
        if (newValue != null && newValue.status() == ChangeFieldStatus.CHANGED) {
            setter.accept(newValue.newValue());
            return true;
        }
        return false;
    }
}
