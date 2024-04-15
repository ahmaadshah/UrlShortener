package com.HosseiniAhmad.URLShorterner.controller;

import com.HosseiniAhmad.URLShorterner.dto.subscription.SubscriptionResponseDto;
import com.HosseiniAhmad.URLShorterner.dto.url.binding.UrlBindingCreateRequestDto;
import com.HosseiniAhmad.URLShorterner.dto.url.binding.UrlBindingResponseDto;
import com.HosseiniAhmad.URLShorterner.dto.user.UserResponseDto;
import com.HosseiniAhmad.URLShorterner.model.entity.user.Role;
import com.HosseiniAhmad.URLShorterner.service.SubscriptionService;
import com.HosseiniAhmad.URLShorterner.service.UrlService;
import com.HosseiniAhmad.URLShorterner.service.UserService;
import com.HosseiniAhmad.URLShorterner.service.bill.BillingService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")
public class UserViewController {
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final BillingService billingService;
    private final UrlService urlService;

    @Autowired
    public UserViewController(
            UserService userService,
            SubscriptionService subscriptionService,
            BillingService billingService,
            UrlService urlService
    ) {
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.billingService = billingService;
        this.urlService = urlService;
    }

    @GetMapping("/profile")
    @RolesAllowed(value = {"NOT_CONFIRMED", "REGISTERED", "BLOCKED", "DELETED", "ADMIN"})
    public String getProfile(Model model) {
        UserResponseDto user = userService.getAuthenticatedUser();
        if (user.role() == Role.ROLE_BLOCKED) return "blocked";
        if (user.role() == Role.ROLE_DELETED) return "deleted";
        model.addAttribute("userProfile", user);
        return "user/profile";
    }

    @GetMapping("/subscription")
    @RolesAllowed(value = {"NOT_CONFIRMED", "REGISTERED", "ADMIN"})
    public String getSubscriptions(Model model) {
        UserResponseDto user = userService.getAuthenticatedUser();
        final String expired = "expired";
        final String notPaid = "notPaid";
        final String active = "active";
        Map<String, List<SubscriptionResponseDto>> isActiveToSubscription =
                subscriptionService.getSubscriptionsByUserId(user.id()).stream().collect(Collectors.groupingBy(s ->
                        s.expiryDate().isBefore(LocalDate.now()) ? expired : (!s.isPaid() ? notPaid : active))
                );

        List<SubscriptionResponseDto> activeSubs = sortByExpiryDateDesc(isActiveToSubscription.get(active));
        List<SubscriptionResponseDto> notPaidSubs = sortByExpiryDateDesc(isActiveToSubscription.get(notPaid));
        List<SubscriptionResponseDto> expiredSubs = sortByExpiryDateDesc(isActiveToSubscription.get(expired));
        model.addAttribute("activeSubs", activeSubs);
        model.addAttribute("notPaidSubs", notPaidSubs);
        model.addAttribute("expiredSubs", expiredSubs);
        return "subscription/subscriptions";
    }

    private List<SubscriptionResponseDto> sortByExpiryDateDesc(List<SubscriptionResponseDto> dtos) {
        return dtos == null ? new ArrayList<>() : dtos.stream()
                .sorted(Comparator.comparing(SubscriptionResponseDto::expiryDate).reversed())
                .toList();
    }

    @GetMapping("/subscription/{subscriptionId}/buy")
    @RolesAllowed(value = {"REGISTERED", "ADMIN"})
    public String getSubscriptionBuyingConfirmationPage(@PathVariable("subscriptionId") Long subscriptionId, Model model) {
        SubscriptionResponseDto subscription = subscriptionService.updateSubscription(subscriptionId);
        return setSubscriptionModelAttributes(model, subscription);
    }

    @GetMapping("/subscription/buy")
    @RolesAllowed(value = {"NOT_CONFIRMED", "REGISTERED", "ADMIN"})
    public String getSubscriptionBuyingConfirmationPage(Model model) {
        UserResponseDto user = userService.getAuthenticatedUser();
        if (user.role() == Role.ROLE_NOT_CONFIRMED) return "user/not_confirmed";
        SubscriptionResponseDto subscription = subscriptionService.createSubscription(user.id());
        return setSubscriptionModelAttributes(model, subscription);
    }

    private String setSubscriptionModelAttributes(Model model, SubscriptionResponseDto subscription) {
        model.addAttribute("startDate", subscription.creationDate());
        model.addAttribute("endDate", subscription.expiryDate());
        model.addAttribute("subscriptionId", subscription.id());
        model.addAttribute(
                "price",
                String.format("%.2f", billingService.geCurrentPrice().getPrice().doubleValue())
        );
        return "subscription/confirm_buying";
    }

    @GetMapping("/subscription/deny")
    @RolesAllowed(value = {"REGISTERED", "ADMIN"})
    public String denySubscriptionBuying() {
        return "redirect:/user/subscription";
    }

    @GetMapping("/subscription/{subscriptionId}/confirm")
    @RolesAllowed(value = {"REGISTERED", "ADMIN"})
    public String confirmSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        SubscriptionResponseDto subscriptionResponseDto = subscriptionService.getSubscription(subscriptionId);
        String paymentLink = subscriptionService.getSubscriptionPaymentLink(subscriptionResponseDto.id());
        return "redirect:" + paymentLink;
    }

    @GetMapping("/subscription/{subscriptionId}/delete")
    @RolesAllowed(value = {"REGISTERED", "ADMIN"})
    public String deleteUnpaidSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionService.deleteUnpaidSubscription(subscriptionId);
        return "redirect:/user/subscription";
    }

    @GetMapping("/binding")
    @RolesAllowed(value = {"REGISTERED", "ADMIN"})
    public String getBindingPage(Model model) {
        final String inactive = "inactive";
        final String active = "active";
        Map<String, List<UrlBindingResponseDto>> isActiveToBindings =
                urlService.getUserBindings().stream().collect(Collectors.groupingBy(b ->
                        b.isClosed() || b.expiryDate().isBefore(LocalDate.now()) ? inactive : active)
                );

        List<UrlBindingResponseDto> activeBindings = sortBindingsByExpiryDateDesc(isActiveToBindings.get(active));
        List<UrlBindingResponseDto> inactiveBindings = sortBindingsByExpiryDateDesc(isActiveToBindings.get(inactive));
        model.addAttribute(active, activeBindings);
        model.addAttribute(inactive, inactiveBindings);
        return "url/bindings";
    }

    @GetMapping("/binding/create")
    @RolesAllowed(value = {"REGISTERED", "ADMIN"})
    public String getCreateBindingPage(Model model) {
        model.addAttribute("bindingForm", new UrlBindingCreateRequestDto());
        return "url/create_binding";
    }

    @PostMapping("/binding/create")
    @RolesAllowed(value = {"REGISTERED", "ADMIN"})
    public String createBinding(
            @ModelAttribute("bindingForm") @Valid UrlBindingCreateRequestDto bindingForm,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "url/create_binding";
        }
        urlService.createBinding(bindingForm);
        return "redirect:/user/binding";
    }

    @GetMapping("/binding/{bindingId}/close")
    @RolesAllowed(value = {"REGISTERED", "ADMIN"})
    public String closeBinding(@PathVariable("bindingId") Long bindingId) {
        urlService.closeBinding(bindingId);
        return "redirect:/user/binding";
    }

    private List<UrlBindingResponseDto> sortBindingsByExpiryDateDesc(List<UrlBindingResponseDto> dtos) {
        return dtos == null ? new ArrayList<>() : dtos.stream()
                .sorted(Comparator.comparing(UrlBindingResponseDto::expiryDate).reversed())
                .toList();
    }
}
