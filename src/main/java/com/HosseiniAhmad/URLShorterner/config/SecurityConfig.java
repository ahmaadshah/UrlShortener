package com.HosseiniAhmad.URLShorterner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain formLoginAndBasicFilterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(
                                        "/auth/**",
                                        "/auth/register",
                                        "/error", "/swagger-ui",
                                        "/v3/api-docs"
                                ).permitAll() // какие url открыть
                                .anyRequest().authenticated()) // остальные открыть только для аутентифицированных пользователей
                // Настройка своей формы аутентификации
                .formLogin(form -> form
                        .loginPage("/auth/login") // страница аутентификации
                        .loginProcessingUrl("/process_login") // сюда будет выполнен post-запрос из формы аутентификации. Spring Security сам создаст метод для обработки пароля и логина из формы login.html
                        .defaultSuccessUrl("/user/profile", true) // перенаправляем сюда в случае успешной аутентификации
                        .failureUrl("/auth/login?error") // если аутентификация не удалась, выдаём ту же страницу с параметром error
                )
                .logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/auth/login")) // действия для выхода
                .authenticationManager(manager)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}