package com.HosseiniAhmad.URLShorterner.dto.payment;

import com.HosseiniAhmad.URLShorterner.util.annotation.FormUrlencodedProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Объект, уточняющий параметры получаемогов PayPal токена
 * <br>Пример
 * <pre>
 * --data-urlencode 'grant_type=client_credentials' \
 * --data-urlencode 'ignoreCache=true' \
 * --data-urlencode 'return_authn_schemes=true' \
 * --data-urlencode 'return_client_metadata=true' \
 * --data-urlencode 'return_unconsented_scopes=true'
 * </pre>
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PayPalTokenRequest {
    @FormUrlencodedProperty("grant_type")
    @Builder.Default
    String grantType = "client_credentials";

    @FormUrlencodedProperty("ignoreCache")
    @Builder.Default
    boolean ignoreCache = true;

    @Builder.Default
    @FormUrlencodedProperty("return_authn_schemes")
    boolean returnAuthnSchemes = true;

    @FormUrlencodedProperty("return_client_metadata")
    @Builder.Default
    boolean returnClientMetadata = true;

    @FormUrlencodedProperty("return_unconsented_scopes")
    @Builder.Default
    boolean returnUnconsentedScopes = true;
}
