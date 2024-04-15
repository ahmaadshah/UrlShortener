package com.HosseiniAhmad.URLShorterner.util.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для маппинга корректных имён полей объектов, превращаемых в тело запроса
 * с типом application/x-www-form-urlencoded, аналог JsonProperty
 *
 * @see JsonProperty
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FormUrlencodedProperty {
    String USE_DEFAULT_NAME = "";

    String value() default USE_DEFAULT_NAME;
}
