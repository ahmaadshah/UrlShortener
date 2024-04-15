package com.HosseiniAhmad.URLShorterner.controller;

import com.HosseiniAhmad.URLShorterner.service.SubscriptionService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }


    @GetMapping("/{subscriptionId}/bought")
    @RolesAllowed(value = { "TECHNICAL", "ADMIN", "REGISTERED"})
    public void setSubscriptionIsPaid(@PathVariable Long subscriptionId) {
        subscriptionService.setSubscriptionIsPaid(subscriptionId);
    }
}
