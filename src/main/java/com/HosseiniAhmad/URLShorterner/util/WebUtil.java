package com.HosseiniAhmad.URLShorterner.util;

import com.HosseiniAhmad.URLShorterner.util.annotation.FormUrlencodedProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class WebUtil {
    private WebUtil() {
    }

    public static String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        return "Basic " + new String(encodedAuth);
    }

    public static <T> MultiValueMap<String, String> getFormUrlEncodedBody(T obj, Class<T> clazz) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        Arrays.stream(clazz.getDeclaredFields()).forEach(f -> {
            FormUrlencodedProperty annotation = f.getAnnotation(FormUrlencodedProperty.class);
            String key = annotation == null || StringUtils.isBlank(annotation.value()) ? f.getName() : annotation.value();
            Object value = null;
            try {
                value = f.get(obj);
            } catch (Exception ignore) {
            }
            if (value instanceof Collection<?> col) form.put(key, col.stream().map(Object::toString).toList());
            else form.add(key, Objects.toString(value));
        });
        return form;
    }
}
