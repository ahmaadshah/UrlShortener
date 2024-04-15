package com.HosseiniAhmad.URLShorterner.dto.user;

import com.HosseiniAhmad.URLShorterner.dto.common.ChangedField;
import jakarta.validation.constraints.*;

import static com.HosseiniAhmad.URLShorterner.dto.Constants.PASSWORD_REGEX;

public record UserUpdateCredentialsRequestDto(
        @NotNull @Positive Long id,
        ChangedField<String> username,
        ChangedField<String> password,
        ChangedField<String> passwordConfirmation,
        ChangedField<String> email
) { }
