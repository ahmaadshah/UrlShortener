package com.HosseiniAhmad.URLShorterner.dto.user;

import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserUpdateRoleRequestDto(
        @NotNull @Positive Long id,
        @NotNull Role newRole
) { }
