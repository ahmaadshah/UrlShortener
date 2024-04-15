package com.HosseiniAhmad.URLShorterner.dto.url.binding;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlBindingCreateRequestDto {
    private @NotBlank @URL(message = "Invalid URL format") String longUrl;
    private @Future(message = "Expiry date must be in the future or absent") LocalDate expiryDate;
}
