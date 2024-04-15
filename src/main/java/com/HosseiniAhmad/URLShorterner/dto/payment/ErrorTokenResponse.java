package com.HosseiniAhmad.URLShorterner.dto.payment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ErrorTokenResponse(
        @JsonProperty("error") String error,
        @JsonProperty("error_description") String errorDescription,
        @JsonProperty("supported_authn_schemes") List<String> supportedAuthnSchemes
) {

}
