package com.HosseiniAhmad.URLShorterner.util.validator;

import com.HosseiniAhmad.URLShorterner.dto.common.ChangeFieldStatus;
import com.HosseiniAhmad.URLShorterner.dto.user.UserUpdateCredentialsRequestDto;
import com.HosseiniAhmad.URLShorterner.service.UserSecurityService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.HosseiniAhmad.URLShorterner.dto.Constants.*;

@Component
public class UserUpdateCredentialsRequestValidator implements Validator {
    private static final Pattern PATTERN = Pattern.compile(PASSWORD_REGEX);

    private final UserSecurityService userSecurityService;

    @Autowired
    public UserUpdateCredentialsRequestValidator(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateCredentialsRequestDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateCredentialsRequestDto data = (UserUpdateCredentialsRequestDto) target;
        if (data.password() == null && data.username() != null && data.email() == null) {
            errors.rejectValue(null, NECESSARY_FIELD_ABSENT_MESSAGE);
            return;
        }

        if (data.password() != null && data.password().status() == ChangeFieldStatus.CHANGED) {
            if (!PATTERN.matcher(data.password().newValue()).matches())
                errors.rejectValue("password", BAD_PASSWORD_MESSAGE);
            if (!Objects.equals(data.password().newValue(), data.passwordConfirmation().newValue()))
                errors.rejectValue("passwordConfirmation", BAD_PASSWORD_CONFIRMATION_MESSAGE);
        }

        if (data.username() != null && data.username().status() == ChangeFieldStatus.CHANGED) {
            String username = data.username().newValue();
            if (StringUtils.isBlank(username) || username.length() > 100)
                errors.rejectValue("username", BAD_USERNAME_MESSAGE);
            if (userSecurityService.getUserByUsername(username).isPresent())
                errors.rejectValue("username", USERNAME_IS_BUSY_MESSAGE);
        }

        EmailValidator emailValidator = new EmailValidator();
        // Инициализация не требуется, поскольку данный валидатор не использует параметры аннотации
        emailValidator.initialize(null);
        if (data.email() != null && data.email().status() == ChangeFieldStatus.CHANGED) {
            String email = data.email().newValue();
            if (!emailValidator.isValid(email, null))  // Контекст ConstraintValidatorContext передается как null, поскольку он не используется в данной реализации
                errors.rejectValue("email", BAD_EMAIL_MESSAGE);
            if (userSecurityService.getUserByEmail(email).isPresent()) {
                errors.rejectValue("email", EMAIL_IS_BUSY_MESSAGE);
            }
        }
    }
}
