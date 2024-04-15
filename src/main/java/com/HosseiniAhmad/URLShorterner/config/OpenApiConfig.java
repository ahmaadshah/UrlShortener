package com.HosseiniAhmad.URLShorterner.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
// TODO перепишите данные под себя

@OpenAPIDefinition(
        info = @Info(
                title = "Example Api",
                description = "For demonstration", version = "1.0.0",
                contact = @Contact(
                        name = "Ahmad Hosseini",
                        email = "ahmadhosseini91@icloud.com",
                        url = "https://ahmad.javadev.com"
                )
        )
)
@SecurityScheme(
        name = "FormAuth",
        type = SecuritySchemeType.APIKEY, // APIKEY используется для описания, но реальная аутентификация через форму не поддерживается Swagger'ом напрямую
        in = SecuritySchemeIn.COOKIE, // Предполагаем использование cookie для сессии
        paramName = "JSESSIONID" // Имя cookie, используемое для сессии в Spring Security
)
public class OpenApiConfig { }

