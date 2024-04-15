package com.HosseiniAhmad.URLShorterner.dto.common;

public record ChangedField<T>(T newValue, ChangeFieldStatus status) {
}
