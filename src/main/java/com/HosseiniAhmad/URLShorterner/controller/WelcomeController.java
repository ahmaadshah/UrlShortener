package com.HosseiniAhmad.URLShorterner.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping()
public class WelcomeController {

    @PermitAll
    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @PermitAll
    @GetMapping()
    public String getWelcome() {
        return getIndex();
    }
}
