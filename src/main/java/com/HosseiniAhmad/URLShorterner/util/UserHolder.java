package com.HosseiniAhmad.URLShorterner.util;

import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import com.HosseiniAhmad.URLShorterner.security.UserWithCredentials;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {
    public User getUserFromPrincipal(){
        // Обращение к контексту безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // получим бин текущего потока, т.е. пользователя
        return ((UserWithCredentials)authentication.getPrincipal()).user();
    }
}
