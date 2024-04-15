package com.HosseiniAhmad.URLShorterner.dto.user;

import com.HosseiniAhmad.URLShorterner.dto.url.binding.UrlBindingResponseDto;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;

import java.util.Set;

public record UserResponseDto(
        Long id,
        String email,
        String username,
        Role role,
        Set<UrlBindingResponseDto> urlBindings
) { }
