package com.HosseiniAhmad.URLShorterner.controller;

import com.HosseiniAhmad.URLShorterner.dto.url.binding.UrlBindingResponseDto;
import com.HosseiniAhmad.URLShorterner.service.UrlService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.HosseiniAhmad.URLShorterner.model.shortener.ShortenerConstants.SHORTENER_PATH_PREFIX;


@Controller
@RequestMapping(SHORTENER_PATH_PREFIX)
public class UrlRedirectController {
    private final UrlService urlService;

    @Autowired
    public UrlRedirectController(UrlService urlService) {
        this.urlService = urlService;
    }


    @GetMapping("/{uid}")
    @PermitAll
    public String redirect(@PathVariable(name = "uid") String uid) {
        UrlBindingResponseDto dto = urlService.getByUid(uid);
        return "redirect:"+ dto.longUrl();
    }
}
