package com.HosseiniAhmad.URLShorterner.mapper;

import com.HosseiniAhmad.URLShorterner.dto.url.binding.UrlBindingResponseDto;
import com.HosseiniAhmad.URLShorterner.model.entity.url.UrlBinding;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UrlBindingMapper {
    public UrlBindingResponseDto toUrlBindingResponseDto(UrlBinding urlBinding) {
        return new UrlBindingResponseDto(
                urlBinding.getId(),
                urlBinding.getShortUrl().toString(),
                urlBinding.getLongUrl(),
                urlBinding.getClicks(),
                urlBinding.getCreationDate(),
                urlBinding.getExpiryDate(),
                urlBinding.isClosed(),
                urlBinding.getUser().getId()
        );
    }

    public Set<UrlBindingResponseDto> toUrlBindingResponseDtoSet(Set<UrlBinding> urlBindings) {
        return urlBindings.stream().map(this::toUrlBindingResponseDto).collect(Collectors.toSet());
    }
}
