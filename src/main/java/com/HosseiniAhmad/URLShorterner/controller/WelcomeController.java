package com.HosseiniAhmad.URLShorterner.controller;

import com.HosseiniAhmad.URLShorterner.dto.subscription.SubscriptionResponseDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import com.HosseiniAhmad.URLShorterner.service.SubscriptionService;
import com.HosseiniAhmad.URLShorterner.service.UserService;
import com.HosseiniAhmad.URLShorterner.service.bill.BillingService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
