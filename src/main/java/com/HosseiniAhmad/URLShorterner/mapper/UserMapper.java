package com.HosseiniAhmad.URLShorterner.mapper;

import com.HosseiniAhmad.URLShorterner.dto.user.UserRegistrationRequestDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.model.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    private final UrlBindingMapper urlBindingMapper;

    @Autowired
    public UserMapper(UrlBindingMapper urlBindingMapper) {
        this.urlBindingMapper = urlBindingMapper;
    }

    public UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                urlBindingMapper.toUrlBindingResponseDtoSet(user.getUrlBindings())
        );
    }

    public List<UserResponseDto> toUserResponseDtoList(List<User> users) {
        return users.stream().map(this::toUserResponseDto).toList();
    }

    public User toUser(UserRegistrationRequestDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build();
    }
}
