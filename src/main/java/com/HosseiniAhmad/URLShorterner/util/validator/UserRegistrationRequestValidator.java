package com.HosseiniAhmad.URLShorterner.util.validator;

import com.HosseiniAhmad.URLShorterner.dto.user.UserRegistrationRequestDto;
import com.HosseiniAhmad.URLShorterner.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

import static com.HosseiniAhmad.URLShorterner.dto.Constants.*;

@Component
public class UserRegistrationRequestValidator implements Validator {
    private final UserSecurityService userSecurityService;

    @Autowired
    public UserRegistrationRequestValidator(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegistrationRequestDto data = (UserRegistrationRequestDto) target;
        if (!Objects.equals(data.getPassword(), data.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation", BAD_PASSWORD_CONFIRMATION_MESSAGE);
            return;
        }

        if (userSecurityService.getUserByUsername(data.getUsername()).isPresent())
            errors.rejectValue("username", USERNAME_IS_BUSY_MESSAGE);

        if (userSecurityService.getUserByEmail(data.getEmail()).isPresent())
            errors.rejectValue("email", EMAIL_IS_BUSY_MESSAGE);
    }
}
