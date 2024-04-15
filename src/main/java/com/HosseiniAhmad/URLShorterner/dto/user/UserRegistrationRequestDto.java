package com.HosseiniAhmad.URLShorterner.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

import static com.HosseiniAhmad.URLShorterner.dto.Constants.BAD_PASSWORD_MESSAGE;
import static com.HosseiniAhmad.URLShorterner.dto.Constants.PASSWORD_REGEX;

/**
 * Транспортный объект для данных регистрации пользователя <br>
 * <a href="https://www.baeldung.com/java-regex-password-validation">See here about password regex</a>
 */
@Data
@NoArgsConstructor
public class UserRegistrationRequestDto {
    private @Size(min = 1, max = 100, message = "Name length could be from 1 to 100 symbols") String username;
    private @NotBlank @Pattern(regexp = PASSWORD_REGEX, message = BAD_PASSWORD_MESSAGE) String password;
    private @NotBlank @Pattern(regexp = PASSWORD_REGEX) String passwordConfirmation;
    private @NotBlank @Email String email;
}
