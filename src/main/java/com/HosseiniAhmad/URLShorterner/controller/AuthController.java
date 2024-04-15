package com.HosseiniAhmad.URLShorterner.controller;

import com.HosseiniAhmad.URLShorterner.dto.user.UserRegistrationRequestDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @PermitAll
    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("userForm", new UserRegistrationRequestDto());
        return "auth/registration";
    }

    @PostMapping("/register")
    @PermitAll
    public String register(
            @ModelAttribute("userForm") @Valid UserRegistrationRequestDto userForm,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            return "auth/registration";
        }
        UserResponseDto newUserResponseDto = userService.createUser(userForm);
        model.addAttribute("newUser", newUserResponseDto);
        return "user/registered";
    }

    @GetMapping("/{id}/email_confirmation")
    @PermitAll
    public String confirmEmail(@PathVariable Long id) {
        userService.setEmailConfirmation(id);
        return "user/confirmation";
    }
}
